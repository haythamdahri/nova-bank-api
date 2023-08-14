package org.newyork.microservices.novabank.controllers;

import lombok.RequiredArgsConstructor;
import org.newyork.microservices.novabank.dto.CheckingAccountDTO;
import org.newyork.microservices.novabank.services.CheckingAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/checking-accounts")
@RequiredArgsConstructor
public class CheckingAccountsController {

    private final CheckingAccountService checkingAccountService;

    @GetMapping(path = "/by-user-account-number/{userAccountNumber}")
    public ResponseEntity<List<CheckingAccountDTO>> getBankUserCheckingAccountsByUserAccountNumber(@PathVariable(name = "userAccountNumber") String userAccountNumber) {
        return ResponseEntity.ok(
                checkingAccountService.getCheckingAccountsByUserAccountNumber(userAccountNumber)
        );
    }

}
