package org.newyork.microservices.novabank.controllers;

import lombok.RequiredArgsConstructor;
import org.newyork.microservices.novabank.dto.CreateSavingAccountRequestDTO;
import org.newyork.microservices.novabank.dto.SavingAccountDTO;
import org.newyork.microservices.novabank.services.SavingAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/saving-accounts")
@RequiredArgsConstructor
public class SavingAccountsController {

    private final SavingAccountService savingAccountService;

    @PostMapping(path = "/")
    public ResponseEntity<SavingAccountDTO> createSavingAccount(
            @RequestBody CreateSavingAccountRequestDTO createSavingAccountRequest
    ) {
        return ResponseEntity.ok(
                savingAccountService.createSavingAccount(createSavingAccountRequest)
        );
    }

    @GetMapping(path = "/by-user-account-number/{userAccountNumber}")
    public ResponseEntity<List<SavingAccountDTO>> getBankUserSavingAccountsByUserAccountNumber(
            @PathVariable(name = "userAccountNumber") String userAccountNumber
    ) {
        return ResponseEntity.ok(
                savingAccountService.getSavingAccountsByUserAccountNumber(userAccountNumber)
        );
    }

    @DeleteMapping(path = "/closing/{accountNumber}")
    public ResponseEntity<Void> closeSavingAccount(@PathVariable(name = "accountNumber") String accountNumber) {
        savingAccountService.closeAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }

}
