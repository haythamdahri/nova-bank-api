package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.BankUserEntity;

@Mapper(componentModel = "spring")
public interface CreateBankUserMapper extends GenericMapper<CreateBankUserRequestDTO, BankUserEntity> {
}
