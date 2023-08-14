package org.newyork.microservices.novabank.batch.processors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountEntity;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.newyork.microservices.novabank.mappers.CheckingAccountTransactionMapper;
import org.newyork.microservices.novabank.services.CheckingAccountService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CheckingAccountTransactionItemProcessor implements ItemProcessor<TransactionDTO, CheckingAccountTransactionEntity> {

    private final CheckingAccountTransactionMapper checkingAccountTransactionMapper;
    private final CheckingAccountService checkingAccountService;

    @Override
    public CheckingAccountTransactionEntity process(final TransactionDTO transaction) {
        log.info("Mapping transaction: {}", transaction);
        try {
            final CheckingAccountEntity checkingAccount = checkingAccountService.getCheckingAccount(
                    transaction.getAccountNumber()
            );
            final CheckingAccountTransactionEntity checkingAccountTransactionEntity = checkingAccountTransactionMapper
                    .toEntity(transaction);
            checkingAccountTransactionEntity.setAccount(checkingAccount);
            return checkingAccountTransactionEntity;
        } catch (Exception e) {
            log.error("Fatal error while mapping transaction", e);
            return null;
        }
    }
}
