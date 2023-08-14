package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetBankUserPasswordRequestDTO implements Serializable {

    @JsonProperty("userAccountNumber")
    private String userAccountNumber;

    @JsonProperty("password")
    private String[] password;

}
