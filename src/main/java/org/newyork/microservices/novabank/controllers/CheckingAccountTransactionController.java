package org.newyork.microservices.novabank.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.dto.TransactionSearchRequestDTO;
import org.newyork.microservices.novabank.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/transactions")
public class CheckingAccountTransactionController {

    private final TransactionService checkingAccountTransactionService;

    @GetMapping(path = "/bank-users/{userAccountNumber}/checking-accounts")
    public ResponseEntity<Page<TransactionDTO>> getBankUserCheckingAccountsTransactions(
            @PathVariable(name = "userAccountNumber") String userAccountNumber,
            @RequestParam(name = "page", defaultValue = "${nova.bank.defaults.page}") int page,
            @RequestParam(name = "size", defaultValue = "${nova.bank.defaults.size}") int size,
            @RequestParam(name = "sort", defaultValue = "operationDate") String[] sort,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(
                checkingAccountTransactionService.getCheckingAccountTransactionsByBankUser(
                        userAccountNumber, page, size, direction, sort
                )
        );
    }

    @GetMapping(path = "/checking-accounts/{accountNumber}")
    public ResponseEntity<Page<TransactionDTO>> getCheckingAccountTransactions(
            @PathVariable(name = "accountNumber") String accountNumber,
            @RequestParam(name = "startDateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(name = "page", defaultValue = "${nova.bank.defaults.page}") int page,
            @RequestParam(name = "size", defaultValue = "${nova.bank.defaults.size}") int size,
            @RequestParam(name = "sort", defaultValue = "operationDate") String[] sort,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(
                checkingAccountTransactionService.getCheckingAccountTransactions(
                        TransactionSearchRequestDTO.builder()
                                .accountNumber(accountNumber)
                                .startDateTime(Optional.ofNullable(startDate).map(LocalDate::atStartOfDay).orElse(null))
                                .endDateTime(Optional.ofNullable(endDate).map(endDateValue -> endDateValue.atTime(LocalTime.MAX)).orElse(null))
                                .build(),
                        page, size, direction, sort
                )
        );
    }

}
