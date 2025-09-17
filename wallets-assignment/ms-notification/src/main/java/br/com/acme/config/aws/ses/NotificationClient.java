package br.com.acme.config.aws.ses;

import java.math.BigDecimal;

public interface NotificationClient {

    void notification(String toEmail, String codeTransaction, BigDecimal amount);
}
