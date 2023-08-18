package org.newyork.microservices.novabank.controllers;

import org.newyork.microservices.novabank.entities.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/transactions-config")
public class TransactionConfigController {

    @GetMapping(path = "/operations")
    public Operation[] getTransactionOperations() {
        return Operation.values();
    }

}
