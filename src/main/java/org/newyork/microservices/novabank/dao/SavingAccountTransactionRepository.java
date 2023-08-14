package org.newyork.microservices.novabank.dao;

import org.newyork.microservices.novabank.entities.SavingAccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingAccountTransactionRepository extends PagingAndSortingRepository<SavingAccountTransactionEntity, Long>,
        JpaRepository<SavingAccountTransactionEntity, Long> {

    List<SavingAccountTransactionEntity> findByAccount_AccountNumber(@Param("accountNumber") String accountNumber);

}
