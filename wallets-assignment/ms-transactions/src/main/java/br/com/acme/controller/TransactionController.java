package br.com.acme.controller;

import br.com.acme.controller.api.TransactionResource;
import br.com.acme.controller.request.TransactionRequest;
import br.com.acme.controller.response.BalanceWalletInstant;
import br.com.acme.controller.response.BalanceWalletResponse;
import br.com.acme.controller.response.TransactionConfirmedResponse;
import br.com.acme.controller.response.TransactionResponse;
import br.com.acme.domain.TypeTransaction;
import br.com.acme.domain.model.TransactionDomain;
import br.com.acme.mapper.ConverterMapper;
import br.com.acme.usecases.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController implements TransactionResource {

    private final IPerformTransactionService performTransactionService;
    private final ICheckBalanceWalletService checkBalanceWalletService;
    private final IGetTransactionsByPeriod transactionsByPeriod;

    private final CloudWatchLogService cloudWatchLogService;
    private final IGetBalanceWalletInstantPass getBalanceWalletInstantPass;
    private final ConverterMapper converterMapper;


    public TransactionController(IPerformTransactionService performTransactionService,
                                 IPerformWithdrawTransactionService performWithdrawTransactionService,
                                 IPerformDepositTransactionService performDepositTransactionService,
                                 IPerformTransferTransactionService performTransferTransactionService,
                                 ICheckBalanceWalletService checkBalanceWalletService, IGetTransactionsByPeriod transactionsByPeriod,
                                 CloudWatchLogService cloudWatchLogService,
                                 IGetBalanceWalletInstantPass getBalanceWalletInstantPass,
                                 ConverterMapper converterMapper) {
        this.performTransactionService = performTransactionService;
        this.checkBalanceWalletService = checkBalanceWalletService;
        this.transactionsByPeriod = transactionsByPeriod;
        this.cloudWatchLogService = cloudWatchLogService;
        this.getBalanceWalletInstantPass = getBalanceWalletInstantPass;
        this.converterMapper = converterMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionConfirmedResponse performTransaction(@Valid @RequestBody TransactionRequest request) {

        var domain = (TransactionDomain) this.converterMapper.convertObject(request, TransactionDomain.class);
        this.cloudWatchLogService.sendLog("Controller Layer -> Perform Transaction :: " + request);
        return createTransactionResponse(this.performTransactionService.createTransaction(domain));
    }


    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BalanceWalletInstant balanceWalletPass(@RequestParam("walletNumber") String walletNumber,
                                                  @RequestParam("datePass")
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime datePass) {


        var response = this.getBalanceWalletInstantPass.getBalanceByWalletAndDate(walletNumber, datePass);
        return BalanceWalletInstant.builder()
                .balance("Balance to this wallet on " + datePass+ " was " + response)
                .build();
    }

    @Override
    @GetMapping("/check-balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceWalletResponse checkBalance(@RequestParam("walletNumber") String walletNumber) {
        var response  = this.checkBalanceWalletService.checkBalanceWallet(walletNumber);
        return BalanceWalletResponse.builder()
                .message("Actual balance to wallet number " + walletNumber + " is R$ "+response)
                .build();
    }

    @Override
    @GetMapping("/transactions-period")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> transactionByPeriod(
            @RequestParam("walletNumber") String walletNumber,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime  endDate) {
        var responses = this.transactionsByPeriod.getTransactionsPeriod(walletNumber, startDate, endDate);
        return (List<TransactionResponse>) this.converterMapper.convertLIstObjects(responses, TransactionResponse.class);
    }

    private TransactionConfirmedResponse createTransactionResponse(TransactionDomain domain) {

        return TransactionConfirmedResponse.builder()
                .codeTransaction(domain.getCodeTransaction())
                .message("Transaction performed with success.")
                .build();
    }
}
