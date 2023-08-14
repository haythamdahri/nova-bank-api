package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.newyork.microservices.novabank.dto.SavingAccountDTO;
import org.newyork.microservices.novabank.entities.SavingAccountEntity;

@Mapper(componentModel = "spring")
public interface SavingAccountMapper extends GenericMapper<SavingAccountDTO, SavingAccountEntity> {

    @Mappings(
            value = {
                    @Mapping(source = "entity.bankUser.userAccountNumber", target = "userAccountNumber"),
                    @Mapping(source = "entity.accountNumber", target = "accountNumber")
            }
    )
    SavingAccountDTO toDTO(final SavingAccountEntity entity);

}
