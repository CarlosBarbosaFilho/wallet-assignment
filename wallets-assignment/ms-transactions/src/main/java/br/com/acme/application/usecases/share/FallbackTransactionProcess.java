package br.com.acme.application.usecases.share;

import br.com.acme.adapters.output.client.requests.DepositRequest;
import br.com.acme.adapters.output.client.requests.TransferRequest;
import br.com.acme.adapters.output.client.requests.WithdrawRequest;
import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.ports.out.IPerformDepositTransactionRepository;
import br.com.acme.application.ports.out.IPerformTransferTransactionRepository;
import br.com.acme.application.ports.out.IPerformWithdrawTransactionRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.util.Set;

@UseCase
@AllArgsConstructor
public class FallbackTransactionProcess {

    private static final Logger logger = LoggerFactory.getLogger(FallbackTransactionProcess.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final CompleteTransactionProcess completeTransactionProcess;

    private final IPerformTransferTransactionRepository performTransferTransactionService;
    private final IPerformWithdrawTransactionRepository performWithdrawTransactionService;
    private final IPerformDepositTransactionRepository performDepositTransactionService;


    @Scheduled(cron = "0 */1 * * * *")
    public void reprocessTransactions() {
        logger.info("Scheduler in action ....");
        Set<String> keys = redisTemplate.keys("transactions:*");
        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            TransactionDomain tx = (TransactionDomain) redisTemplate.opsForValue().get(key);
            reprocessTransaction(tx, key);
        }
        logger.info("Scheduler finished ....");
    }

    public void reprocessTransaction(TransactionDomain tx, String key) {
        if (tx == null) {
            logger.warn("Transaction not found for key: {}", key);
            return;
        }
        logger.info("Reprocessing transaction: {}", tx.getCodeTransaction());
        processTransaction(tx);
        redisTemplate.delete(key);
        logger.info("Transaction {} removed from Redis", tx.getCodeTransaction());
    }

    public void processTransaction(TransactionDomain tx) {
        tx.setStatusTransaction(StatusTransaction.COMPLETED);
        switch (tx.getTypeTransaction()) {
            case DEPOSIT -> {
                tx.setStatusTransaction(StatusTransaction.COMPLETED);
                performDepositTransactionService.performDeposit(createDepositRequest(tx));
                completeTransactionProcess.completeTransaction(tx);
            }

            case WITHDRAW ->{
                performWithdrawTransactionService.performWithdraw(createWithdrawRequest(tx));
                completeTransactionProcess.completeTransaction(tx);
            }
            case TRANSFER ->{
                performTransferTransactionService.performTransfer(createTransferRequest(tx));
                completeTransactionProcess.completeTransaction(tx);
            }
            default -> logger.error("Unsupported transaction type: {}", tx.getTypeTransaction());
        }
    }

    public void fallbackCreateTransactionOnRedis(TransactionDomain transactionDomain) {
        var buildKey = "transactions:" + transactionDomain.getCodeTransaction().toString();
        redisTemplate.opsForValue().set(buildKey, transactionDomain, Duration.ofDays(1));
    }

    private DepositRequest createDepositRequest(TransactionDomain transactionDomain) {
        return DepositRequest.builder()
                .amount(transactionDomain.getAmountTransaction())
                .destinationWallet(transactionDomain.getDestinationWallet())
                .build();
    }

    private WithdrawRequest createWithdrawRequest(TransactionDomain transactionDomain) {
        return WithdrawRequest.builder()
                .amount(transactionDomain.getAmountTransaction())
                .sourceWallet(transactionDomain.getDestinationWallet())
                .build();
    }

    private TransferRequest createTransferRequest(TransactionDomain transactionDomain) {
        return TransferRequest.builder()
                .amount(transactionDomain.getAmountTransaction())
                .sourceWallet(transactionDomain.getDestinationWallet())
                .destinationWallet(transactionDomain.getDestinationWallet())
                .build();
    }


}
