package org.newyork.microservices.novabank.services.impl;

import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.AccountEntity;
import org.newyork.microservices.novabank.services.RiskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RiskServiceImpl implements RiskService {

    @Override
    public AccountEntity.RiskLevel estimateRiskLevel(final CreateBankUserRequestDTO createBankUserRequest) {
        final long age = ChronoUnit.YEARS.between(LocalDate.now(), createBankUserRequest.getBirthDate());
        if (age > 30 && createBankUserRequest.getAverageIncome().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return AccountEntity.RiskLevel.LOW;
        }
        if (age < 23 && createBankUserRequest.getAverageIncome().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return AccountEntity.RiskLevel.LOW;
        }
        if (age > 23 && age < 29 && createBankUserRequest.getAverageIncome().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return AccountEntity.RiskLevel.AVERAGE;
        }
        if (age > 40 && createBankUserRequest.getAverageIncome().compareTo(BigDecimal.valueOf(10000)) <= 0) {
            return AccountEntity.RiskLevel.HIGH;
        }
        return AccountEntity.RiskLevel.RISKY;
    }

}
