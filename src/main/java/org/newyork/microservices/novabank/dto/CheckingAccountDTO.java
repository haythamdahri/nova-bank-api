package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccountDTO extends AccountDTO {

    @JsonProperty("availableAmount")
    private BigDecimal availableAmount;

    @JsonProperty("accountedAmount")
    private BigDecimal accountedAmount;

    @JsonProperty("authorizedOverdraftAmount")
    private BigDecimal authorizedOverdraftAmount;

}
