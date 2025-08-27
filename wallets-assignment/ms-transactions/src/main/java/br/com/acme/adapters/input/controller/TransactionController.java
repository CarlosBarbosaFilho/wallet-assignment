package br.com.acme.adapters.input.controller;

import br.com.acme.adapters.input.web.api.TransactionResource;
import br.com.acme.adapters.input.web.request.TransactionTransferRequest;
import br.com.acme.adapters.input.web.response.BalanceWalletInstant;
import br.com.acme.adapters.input.web.response.BalanceWalletResponse;
import br.com.acme.adapters.input.web.response.TransactionConfirmedResponse;
import br.com.acme.adapters.input.web.response.TransactionResponse;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.in.IGetBalanceWalletInstantPassUseCase;
import br.com.acme.application.ports.in.IGetTransactionsByPeriodUseCase;
import br.com.acme.application.ports.in.IPerformTransactionUseCase;
import br.com.acme.application.ports.out.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController implements TransactionResource {

    private final IPerformTransactionUseCase performTransactionService;
    private final ICheckBalanceWalletRepository checkBalanceWalletService;
    private final IGetTransactionsByPeriodUseCase transactionsByPeriod;

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final IGetBalanceWalletInstantPassUseCase getBalanceWalletInstantPass;
    private final ConverterMapper converterMapper;


    public TransactionController(IPerformTransactionUseCase performTransactionService,
                                 IPerformWithdrawTransactionRepository performWithdrawTransactionService,
                                 IPerformDepositTransactionRepository performDepositTransactionService,
                                 IPerformTransferTransactionRepository performTransferTransactionService,
                                 ICheckBalanceWalletRepository checkBalanceWalletService, IGetTransactionsByPeriodUseCase transactionsByPeriod,
                                 CreateLogsCloudWatch createLogsCloudWatch,
                                 IGetBalanceWalletInstantPassUseCase getBalanceWalletInstantPass,
                                 ConverterMapper converterMapper) {
        this.performTransactionService = performTransactionService;
        this.checkBalanceWalletService = checkBalanceWalletService;
        this.transactionsByPeriod = transactionsByPeriod;
        this.createLogsCloudWatch = createLogsCloudWatch;
        this.getBalanceWalletInstantPass = getBalanceWalletInstantPass;
        this.converterMapper = converterMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionConfirmedResponse performTransaction(@Valid @RequestBody TransactionTransferRequest request) {

        var domain = (TransactionDomain) this.converterMapper.convertObject(request, TransactionDomain.class);
        this.createLogsCloudWatch.sendLog("Controller Layer -> Perform Transaction :: " + request);
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
