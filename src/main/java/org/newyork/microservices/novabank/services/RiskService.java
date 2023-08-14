package org.newyork.microservices.novabank.services;

import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.AccountEntity;

public interface RiskService {

    AccountEntity.RiskLevel estimateRiskLevel(final CreateBankUserRequestDTO createBankUserRequestDTO);

}
