package org.newyork.microservices.novabank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBankUserRequestDTO implements Serializable {

    @JsonProperty("userAccountNumber")
    private String userAccountNumber;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("address")
    private String address;

    @JsonProperty("birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("job")
    private String job;

}
