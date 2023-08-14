package org.newyork.microservices.novabank.dao;

import org.newyork.microservices.novabank.entities.CheckingAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingAccountRepository extends
        PagingAndSortingRepository<CheckingAccountEntity, Long>,
        CrudRepository<CheckingAccountEntity, Long> {
    List<CheckingAccountEntity> findByBankUser_userAccountNumber(
            @Param("userAccountNumber") final String userAccountNumber
    );

    CheckingAccountEntity findByAccountNumber(@Param("accountNumber") String accountNumber);
}
