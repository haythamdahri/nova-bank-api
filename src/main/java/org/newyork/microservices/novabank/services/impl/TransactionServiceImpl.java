package org.newyork.microservices.novabank.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.newyork.microservices.novabank.dao.CheckingAccountTransactionRepository;
import org.newyork.microservices.novabank.dao.SavingAccountTransactionRepository;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountEntity;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.newyork.microservices.novabank.entities.Operation;
import org.newyork.microservices.novabank.mappers.CheckingAccountTransactionMapper;
import org.newyork.microservices.novabank.mappers.SavingAccountTransactionMapper;
import org.newyork.microservices.novabank.services.CheckingAccountService;
import org.newyork.microservices.novabank.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private final CheckingAccountTransactionMapper checkingAccountSavingAccountTransactionMapper;
    private final SavingAccountTransactionMapper savingAccountTransactionMapper;
    private final CheckingAccountTransactionRepository checkingAccountTransactionRepository;
    private final SavingAccountTransactionRepository savingAccountTransactionRepository;

    @Override
    public Page<TransactionDTO> getCheckingAccountTransactionsByBankUser(
            final String userAccountNumber, final int page, final int size, final String direction, final String... sort
    ) {
        log.info("Retrieving checking account transactions by user account: {}", userAccountNumber);
        return checkingAccountTransactionRepository.findByAccount_BankUser_UserAccountNumber(
                        userAccountNumber,
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Direction.valueOf(StringUtils.toRootUpperCase(direction)), sort)
                        )
                ).map(checkingAccountSavingAccountTransactionMapper::toDTO);
    }

    @Override
    public List<TransactionDTO> getCheckingAccountTransactions(final String accountNumber) {
        log.info("Retrieving checking account transactions: {}", accountNumber);
        return checkingAccountSavingAccountTransactionMapper.toDTO(
                checkingAccountTransactionRepository.findByAccount_AccountNumber(accountNumber)
        );
    }

    @Override
    public Page<TransactionDTO> getCheckingAccountTransactionsPage(
            final String accountNumber, final int page, final int size, final String direction, final String... sort
    ) {
        return checkingAccountTransactionRepository.findByAccount_AccountNumber(
                accountNumber, PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(StringUtils.toRootUpperCase(direction)), sort))
        ).map(checkingAccountSavingAccountTransactionMapper::toDTO);
    }

    @Override
    public List<TransactionDTO> getSavingAccountTransactions(String accountNumber) {
        log.info("Retrieving saving account transactions: {}", accountNumber);
        return savingAccountTransactionMapper.toDTO(
                savingAccountTransactionRepository.findByAccount_AccountNumber(accountNumber)
        );
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveCheckingAccountTransaction(
            final CheckingAccountTransactionEntity checkingAccountTransactionEntity
    ) {
        log.info("Saving checking account transaction: {}", checkingAccountTransactionEntity);
        final BigDecimal updatedAmount = ofNullable(checkingAccountTransactionEntity.getAccount())
                .map(CheckingAccountEntity::getAccountedAmount)
                .map(accountedAmount -> accountedAmount.add(computeTransactionAmount(checkingAccountTransactionEntity)))
                .orElse(computeTransactionAmount(checkingAccountTransactionEntity));
        checkingAccountTransactionEntity.getAccount().setAccountedAmount(updatedAmount);
        checkingAccountTransactionEntity.getAccount().setAvailableAmount(updatedAmount);
        checkingAccountTransactionRepository.save(checkingAccountTransactionEntity);
    }

    private BigDecimal computeTransactionAmount(final CheckingAccountTransactionEntity checkingAccountTransactionEntity) {
        return checkingAccountTransactionEntity.getOperation() == Operation.CREDIT ?
                checkingAccountTransactionEntity.getAmount() : checkingAccountTransactionEntity.getAmount().negate();
    }

}
