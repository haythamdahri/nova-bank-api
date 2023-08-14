package org.newyork.microservices.novabank.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.newyork.microservices.novabank.dto.BankUserDTO;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.dto.UpdateBankUserRequestDTO;
import org.newyork.microservices.novabank.services.BankUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bank-users")
@RequiredArgsConstructor
public class BankUserController {

    private final BankUserService bankUserService;

    @PostMapping(path = "/")
    public ResponseEntity<BankUserDTO> createBankUser(@RequestBody CreateBankUserRequestDTO createBankUserRequest) {
        return ResponseEntity.ok(
                bankUserService.createBankUser(createBankUserRequest)
        );
    }

    @PutMapping(path = "/")
    public ResponseEntity<BankUserDTO> updateBankUser(@RequestBody UpdateBankUserRequestDTO updateBankUserRequest) {
        return ResponseEntity.ok(
                bankUserService.updateBankUser(updateBankUserRequest)
        );
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<BankUserDTO>> getBankUsers(
            @RequestParam(name = "search") String search
    ) {
        final List<BankUserDTO> bankUsers;
        if (StringUtils.isEmpty(search)) {
            bankUsers = bankUserService.getBankUsers();
        } else {
            bankUsers = bankUserService.searchBankUsers(search);
        }
        return ResponseEntity.ok(bankUsers);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BankUserDTO> getBankUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(
                bankUserService.getBankUser(id)
        );
    }

    @GetMapping(path = "/by-user-account-number/{userAccountNumber}")
    public ResponseEntity<BankUserDTO> getBankUserByUserAccountNumber(
            @PathVariable(name = "userAccountNumber") String userAccountNumber
    ) {
        return ResponseEntity.ok(
                bankUserService.getBankUserByUserAccountNumber(userAccountNumber)
        );
    }

}
