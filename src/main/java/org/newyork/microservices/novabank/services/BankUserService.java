package org.newyork.microservices.novabank.services;

import org.newyork.microservices.novabank.dto.BankUserDTO;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.dto.ResetBankUserPasswordRequestDTO;
import org.newyork.microservices.novabank.dto.UpdateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.BankUserEntity;

import java.util.List;

public interface BankUserService {

    BankUserDTO createBankUser(final CreateBankUserRequestDTO createBankUserRequest);

    BankUserDTO updateBankUser(final UpdateBankUserRequestDTO updateBankUserRequest);

    BankUserEntity getBankUserEntity(final String userAccountNumber);

    BankUserDTO getBankUser(final Long id);

    BankUserDTO getBankUserByUserAccountNumber(final String userAccountNumber);

    List<BankUserDTO> getBankUsers();

    List<BankUserDTO> searchBankUsers(final String search);

    void resetPassword(final ResetBankUserPasswordRequestDTO resetBankUserPasswordRequest);

}
