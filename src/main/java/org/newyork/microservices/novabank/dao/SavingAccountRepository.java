package org.newyork.microservices.novabank.dao;

import org.newyork.microservices.novabank.entities.SavingAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingAccountRepository extends PagingAndSortingRepository<SavingAccountEntity, Long>, CrudRepository<SavingAccountEntity, Long> {

    List<SavingAccountEntity> findByBankUser_userAccountNumber(@Param("userAccountNumber") final String userAccountNumber);

    Optional<SavingAccountEntity> findByAccountNumber(@Param("accountNumber") String accountNumber);

    void deleteByAccountNumber(@Param("accountNumber") String accountNumber);

}
