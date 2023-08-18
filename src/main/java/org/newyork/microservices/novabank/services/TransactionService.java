package org.newyork.microservices.novabank.services;

import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.dto.TransactionSearchRequestDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    Page<TransactionDTO> getCheckingAccountTransactionsByBankUser(
            final String accountNumber, final int page, final int size, final String direction, final String... sort
    );

    Page<TransactionDTO> getCheckingAccountTransactions(
            final TransactionSearchRequestDTO transactionSearchRequest,
            final int page,
            final int size,
            final String direction,
            final String... sort
    );

    List<TransactionDTO> getCheckingAccountTransactions(final String accountNumber);

    Page<TransactionDTO> getCheckingAccountTransactionsPage(
            final String accountNumber, final int page, final int size, final String direction, final String... sort
    );

    List<TransactionDTO> getSavingAccountTransactions(final String accountNumber);

    void saveCheckingAccountTransaction(final CheckingAccountTransactionEntity checkingAccountTransactionEntity);

}
