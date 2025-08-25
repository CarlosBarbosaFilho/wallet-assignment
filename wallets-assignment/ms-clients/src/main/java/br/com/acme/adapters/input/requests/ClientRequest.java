package br.com.acme.adapters.input.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Field name is required")
    private String name;

    @NotBlank(message = "Field email is required")
    private String email;

    @NotBlank(message = "Field document is required")
    private String document;

    private BigDecimal income;
}
