package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;

@Mapper(componentModel = "spring")
public interface CheckingAccountTransactionMapper extends GenericMapper<TransactionDTO, CheckingAccountTransactionEntity> {

    @Mappings(
            value = {
                    @Mapping(source = "entity.account.accountNumber", target = "accountNumber"),
            }
    )
    TransactionDTO toDTO(final CheckingAccountTransactionEntity entity);

}
