package org.newyork.microservices.novabank.controllers;

import lombok.RequiredArgsConstructor;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(name = "page", defaultValue = "${nova.bank.defaults.page}") int page,
            @RequestParam(name = "size", defaultValue = "${nova.bank.defaults.size}") int size,
            @RequestParam(name = "sort", defaultValue = "operationDate") String[] sort,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(
                checkingAccountTransactionService.getCheckingAccountTransactionsPage(
                        accountNumber, page, size, direction, sort
                )
        );
    }

}
