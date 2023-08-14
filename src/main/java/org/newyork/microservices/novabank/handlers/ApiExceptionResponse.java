package org.newyork.microservices.novabank.handlers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionResponse implements Serializable {

    @JsonProperty("timestamp")
    @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("httpStatus")
    private HttpStatus httpStatus;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private Object details;

}
