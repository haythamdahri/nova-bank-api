package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.newyork.microservices.novabank.dto.CheckingAccountDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountEntity;

@Mapper(componentModel = "spring")
public interface CheckingAccountMapper extends GenericMapper<CheckingAccountDTO, CheckingAccountEntity> {

    @Mappings(
            value = {
                    @Mapping(source = "entity.bankUser.userAccountNumber", target = "userAccountNumber"),
                    @Mapping(source = "entity.accountNumber", target = "accountNumber")
            }
    )
    CheckingAccountDTO toDTO(final CheckingAccountEntity entity);

}
