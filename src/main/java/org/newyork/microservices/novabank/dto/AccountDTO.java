package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.newyork.microservices.novabank.entities.AccountEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Serializable {

    @JsonProperty("riskLevel")
    private AccountEntity.RiskLevel riskLevel;

    @JsonProperty("userAccountNumber")
    private String userAccountNumber;

    @JsonProperty("accountNumber")
    private String accountNumber;

}
