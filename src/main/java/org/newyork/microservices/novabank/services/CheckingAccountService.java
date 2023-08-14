package org.newyork.microservices.novabank.services;

import org.newyork.microservices.novabank.dto.CheckingAccountDTO;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.newyork.microservices.novabank.entities.CheckingAccountEntity;

import java.util.List;

public interface CheckingAccountService {

    CheckingAccountDTO createCheckingAccount(
            final BankUserEntity bankUser,
            final CreateBankUserRequestDTO createBankUserRequestDTO
    );

    CheckingAccountEntity getCheckingAccount(final String accountNumber);

    List<CheckingAccountDTO> getCheckingAccountsByUserAccountNumber(final String userAccountNumber);

}
