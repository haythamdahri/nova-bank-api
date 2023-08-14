package org.newyork.microservices.novabank.batch.writers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.newyork.microservices.novabank.services.TransactionService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import static org.apache.commons.collections4.IterableUtils.emptyIfNull;

@Component
@RequiredArgsConstructor
public class CheckingAccountTransactionItemWriter implements ItemWriter<CheckingAccountTransactionEntity> {

    private final TransactionService transactionService;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void write(final Chunk<? extends CheckingAccountTransactionEntity> chunk) {
        emptyIfNull(chunk.getItems()).forEach(transactionService::saveCheckingAccountTransaction);
    }

}
