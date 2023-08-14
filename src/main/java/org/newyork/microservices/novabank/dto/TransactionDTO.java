package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.newyork.microservices.novabank.entities.Operation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO implements Serializable {

    @JsonProperty("operation")
    private Operation operation;

    @JsonProperty("operationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime operationDate;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("accountNumber")
    private String accountNumber;

}
