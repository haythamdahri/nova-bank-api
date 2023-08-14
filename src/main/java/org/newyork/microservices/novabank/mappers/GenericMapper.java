package org.newyork.microservices.novabank.mappers;

import java.util.List;

public interface GenericMapper<DTO, ENTITY> {

    DTO toDTO(final ENTITY entity);

    ENTITY toEntity(final DTO dto);

    List<DTO> toDTO(final List<ENTITY> entity);

    List<ENTITY> toEntity(final List<DTO> dto);

}
