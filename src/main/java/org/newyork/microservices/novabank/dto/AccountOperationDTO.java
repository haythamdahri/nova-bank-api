package org.newyork.microservices.novabank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.newyork.microservices.novabank.entities.Operation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountOperationDTO implements Serializable {

    private Operation operation;

    private LocalDateTime localDateTime;

    private BigDecimal amount;

}
