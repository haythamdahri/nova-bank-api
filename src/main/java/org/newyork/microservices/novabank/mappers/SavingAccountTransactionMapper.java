package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.entities.SavingAccountTransactionEntity;

@Mapper(componentModel = "spring")
public interface SavingAccountTransactionMapper extends GenericMapper<TransactionDTO, SavingAccountTransactionEntity> {

    @Mappings(
            value = {
                    @Mapping(source = "entity.account.accountNumber", target = "accountNumber"),
            }
    )
    TransactionDTO toDTO(final SavingAccountTransactionEntity entity);

}
