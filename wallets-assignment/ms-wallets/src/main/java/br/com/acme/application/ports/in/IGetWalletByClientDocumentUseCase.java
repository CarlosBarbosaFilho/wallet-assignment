package br.com.acme.application.ports.in;

import java.math.BigDecimal;

public interface IGetWalletByClientDocumentUseCase {

    BigDecimal getBalanceWalletByClientDocument(String document);
}
