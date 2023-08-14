package org.newyork.microservices.novabank.services;

import org.newyork.microservices.novabank.dto.CreateSavingAccountRequestDTO;
import org.newyork.microservices.novabank.dto.SavingAccountDTO;

import java.util.List;

public interface SavingAccountService {

    List<SavingAccountDTO> getSavingAccountsByUserAccountNumber(final String accountNumber);

    SavingAccountDTO createSavingAccount(final CreateSavingAccountRequestDTO createSavingAccountRequest);

    void closeAccount(final String accountNumber);

}
