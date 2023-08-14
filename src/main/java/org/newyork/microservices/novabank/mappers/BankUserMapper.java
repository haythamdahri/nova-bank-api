package org.newyork.microservices.novabank.mappers;

import org.mapstruct.Mapper;
import org.newyork.microservices.novabank.dto.BankUserDTO;
import org.newyork.microservices.novabank.entities.BankUserEntity;

@Mapper(componentModel = "spring")
public interface BankUserMapper extends GenericMapper<BankUserDTO, BankUserEntity> {
}
