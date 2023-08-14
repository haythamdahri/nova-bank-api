package org.newyork.microservices.novabank.dao;

import jakarta.persistence.criteria.Expression;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankUserRepository extends PagingAndSortingRepository<BankUserEntity, Long>,
        CrudRepository<BankUserEntity, Long>,
        JpaSpecificationExecutor<BankUserEntity> {

    Optional<BankUserEntity> findByUserAccountNumber(@Param("userAccountNumber") final String userAccountNumber);

    @Query(value = "UPDATE BankUserEntity BU SET BU.password = :password WHERE BU.userAccountNumber = :userAccountNumber")
    @Modifying
    int updateUserPassword(
            @Param("userAccountNumber") final String userAccountNumber, @Param("password") final String password
    );

    boolean existsByEmail(@Param("email") String email);


}
