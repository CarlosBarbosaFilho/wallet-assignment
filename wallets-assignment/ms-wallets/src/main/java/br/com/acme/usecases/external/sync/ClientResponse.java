package br.com.acme.usecases.external.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String document;
    private BigDecimal income;
    private String createdAt;
    private StatusClient status;
}
