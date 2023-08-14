package org.newyork.microservices.novabank.dao;

import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingAccountTransactionRepository extends PagingAndSortingRepository<CheckingAccountTransactionEntity, Long>,
        JpaRepository<CheckingAccountTransactionEntity, Long> {

    Page<CheckingAccountTransactionEntity> findByAccount_BankUser_UserAccountNumber(
            @Param("userAccountNumber") String userAccountNumber, @PageableDefault Pageable pageable
    );

    List<CheckingAccountTransactionEntity> findByAccount_AccountNumber(@Param("accountNumber") String accountNumber);

    Page<CheckingAccountTransactionEntity> findByAccount_AccountNumber(
            @Param("accountNumber") String accountNumber, @PageableDefault Pageable pageable
    );

}
