package org.newyork.microservices.novabank.dao;

import org.newyork.microservices.novabank.entities.ClosedSavingAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedSavingAccountRepository extends CrudRepository<ClosedSavingAccountEntity, Long> {

}
