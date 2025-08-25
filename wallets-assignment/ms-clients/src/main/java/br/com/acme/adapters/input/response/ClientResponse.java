package br.com.acme.adapters.input.response;

import br.com.acme.application.domain.StatusClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

    private  Long id;
    private String name;
    private String email;
    private String document;
    private BigDecimal income;
    private String createdAt;
    private StatusClient status;
}
