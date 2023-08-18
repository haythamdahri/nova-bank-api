package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionSearchRequestDTO implements Serializable {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("startDateTime")
    private LocalDateTime startDateTime;

    @JsonProperty("endDateTime")
    private LocalDateTime endDateTime;

}
