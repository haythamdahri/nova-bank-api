package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SavingAccountDTO extends AccountDTO {

    @JsonProperty("availableAmount")
    private BigDecimal availableAmount;

    @JsonProperty("accountedAmount")
    private BigDecimal accountedAmount;

    @JsonProperty("interestRate")
    private BigDecimal interestRate;

}
