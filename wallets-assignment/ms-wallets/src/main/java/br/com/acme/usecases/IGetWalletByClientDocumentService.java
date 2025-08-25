package br.com.acme.usecases;

import br.com.acme.domain.WalletDomain;

import java.math.BigDecimal;

public interface IGetWalletByClientDocumentService {

    BigDecimal getWalletByClientDocument(String document);
}
