package org.newyork.microservices.novabank.services.impl;

import lombok.extern.log4j.Log4j2;
import org.newyork.microservices.novabank.dao.CheckingAccountRepository;
import org.newyork.microservices.novabank.dto.CheckingAccountDTO;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.AccountEntity;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.newyork.microservices.novabank.entities.CheckingAccountEntity;
import org.newyork.microservices.novabank.mappers.CheckingAccountMapper;
import org.newyork.microservices.novabank.services.CheckingAccountService;
import org.newyork.microservices.novabank.services.RiskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class CheckingAccountServiceImpl implements CheckingAccountService {

    private final RiskService riskService;
    private final CheckingAccountRepository checkingAccountRepository;
    private final CheckingAccountMapper checkingAccountMapper;

    public CheckingAccountServiceImpl(
            final RiskService riskService,
            final CheckingAccountRepository checkingAccountRepository,
            final CheckingAccountMapper checkingAccountMapper
    ) {
        this.riskService = riskService;
        this.checkingAccountRepository = checkingAccountRepository;
        this.checkingAccountMapper = checkingAccountMapper;
    }

    @Override
    public CheckingAccountDTO createCheckingAccount(
            final BankUserEntity bankUser,
            final CreateBankUserRequestDTO createBankUserRequest
    ) {
        final AccountEntity.RiskLevel riskLevel = riskService.estimateRiskLevel(createBankUserRequest);
        final BigDecimal authorizedOverdraftAmount = riskLevel.getAuthorizedOverdraftAmount();
        CheckingAccountEntity checkingAccountEntity = CheckingAccountEntity.builder()
                .availableAmount(BigDecimal.ZERO)
                .accountedAmount(BigDecimal.ZERO)
                .authorizedOverdraftAmount(authorizedOverdraftAmount)
                .bankUser(bankUser)
                .riskLevel(riskLevel)
                .accountNumber(UUID.randomUUID().toString().replace("-", ""))
                .build();
        checkingAccountEntity = checkingAccountRepository.save(checkingAccountEntity);
        return checkingAccountMapper.toDTO(checkingAccountEntity);
    }

    @Override
    public CheckingAccountEntity getCheckingAccount(String accountNumber) {
        return checkingAccountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<CheckingAccountDTO> getCheckingAccountsByUserAccountNumber(final String userAccountNumber) {
        return checkingAccountMapper.toDTO(
                checkingAccountRepository.findByBankUser_userAccountNumber(userAccountNumber)
        );
    }

}
