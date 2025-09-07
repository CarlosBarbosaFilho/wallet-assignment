package br.com.acme.adapters.input.controller;

import br.com.acme.adapters.input.web.request.TransactionDeposit;
import br.com.acme.adapters.input.web.request.TransactionTransfer;
import br.com.acme.adapters.input.web.request.TransactionWithdraw;
import br.com.acme.adapters.input.web.api.TransactionResource;
import br.com.acme.adapters.input.web.response.BalanceWalletInstant;
import br.com.acme.adapters.input.web.response.BalanceWalletResponse;
import br.com.acme.adapters.input.web.response.TransactionConfirmedResponse;
import br.com.acme.adapters.input.web.response.TransactionResponse;
import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.TypeTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.in.*;
import br.com.acme.application.ports.out.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController implements TransactionResource {

    private final IGetBalanceWalletInstantPassUseCase getBalanceWalletInstantPass;
    private final IGetTransactionsByPeriodUseCase transactionsByPeriod;

    private final IPerformTransferUseCase performTransferUseCase;
    private final IPerformDepositUseCase performDepositUseCase;
    private final IPerformWithdrawUseCase performWithdrawUseCase;

    private final ICheckBalanceWalletRepository checkBalanceWalletService;

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final ConverterMapper converterMapper;

    @Override
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionConfirmedResponse performTransfer(@Valid @RequestBody TransactionTransfer request) {
        var domain = (TransactionDomain) this.converterMapper.convertObject(request, TransactionDomain.class);
        this.createLogsCloudWatch.sendLog("Controller Layer -> Perform Transaction Transfer:: " + request);
        domain.setTypeTransaction(TypeTransaction.TRANSFER);
        return createTransactionResponse (this.performTransferUseCase.transfer(domain));
    }

    @Override
    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionConfirmedResponse performDeposit(@Valid @RequestBody TransactionDeposit request) {
        var domain = (TransactionDomain) this.converterMapper.convertObject(request, TransactionDomain.class);
        this.createLogsCloudWatch.sendLog("Controller Layer -> Perform Transaction Deposit:: " + request);
        domain.setTypeTransaction(TypeTransaction.DEPOSIT);
        return createTransactionResponse (this.performDepositUseCase.deposit(domain));
    }

    @Override
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionConfirmedResponse performWithdraw(@Valid @RequestBody TransactionWithdraw request) {
        var domain = (TransactionDomain) this.converterMapper.convertObject(request, TransactionDomain.class);
        this.createLogsCloudWatch.sendLog("Controller Layer -> Perform Transaction Withdraw:: " + request);
        domain.setTypeTransaction(TypeTransaction.WITHDRAW);
        return createTransactionResponse (this.performWithdrawUseCase.withdraw(domain));
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
        if (domain.getStatusTransaction().equals(StatusTransaction.FAILED)) {
            return TransactionConfirmedResponse.builder()
                    .codeTransaction(domain.getCodeTransaction())
                    .message("Transaction status is pending, please waiting for processing.")
                    .build();
        }
        return TransactionConfirmedResponse.builder()
                .codeTransaction(domain.getCodeTransaction())
                .message("Transaction performed with success.")
                .build();
    }
}
