package org.newyork.microservices.novabank.services.impl;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SerializationUtils;
import org.newyork.microservices.novabank.dao.ClosedSavingAccountRepository;
import org.newyork.microservices.novabank.dao.SavingAccountRepository;
import org.newyork.microservices.novabank.dto.CreateSavingAccountRequestDTO;
import org.newyork.microservices.novabank.dto.SavingAccountDTO;
import org.newyork.microservices.novabank.entities.AccountEntity;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.newyork.microservices.novabank.entities.ClosedSavingAccountEntity;
import org.newyork.microservices.novabank.entities.SavingAccountEntity;
import org.newyork.microservices.novabank.exceptions.NoSavingAccountFoundException;
import org.newyork.microservices.novabank.mappers.SavingAccountMapper;
import org.newyork.microservices.novabank.services.BankUserService;
import org.newyork.microservices.novabank.services.SavingAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class SavingAccountServiceImpl implements SavingAccountService {

    private final SavingAccountRepository savingAccountRepository;
    private final BankUserService bankUserService;
    private final SavingAccountMapper savingAccountMapper;
    private final ClosedSavingAccountRepository closedSavingAccountRepository;

    public SavingAccountServiceImpl(
            final SavingAccountRepository savingAccountRepository,
            final BankUserService bankUserService,
            final SavingAccountMapper savingAccountMapper,
            final ClosedSavingAccountRepository closedSavingAccountRepository
    ) {
        this.savingAccountRepository = savingAccountRepository;
        this.bankUserService = bankUserService;
        this.savingAccountMapper = savingAccountMapper;
        this.closedSavingAccountRepository = closedSavingAccountRepository;
    }

    @Override
    public List<SavingAccountDTO> getSavingAccountsByUserAccountNumber(final String userAccountNumber) {
        return savingAccountMapper.toDTO(
                savingAccountRepository.findByBankUser_userAccountNumber(userAccountNumber)
        );
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public SavingAccountDTO createSavingAccount(final CreateSavingAccountRequestDTO createSavingAccountRequest) {
        final BankUserEntity bankUserEntity = bankUserService.getBankUserEntity(
                createSavingAccountRequest.getUserAccountNumber()
        );
        final SavingAccountEntity savingAccountEntity = SavingAccountEntity.builder()
                .bankUser(bankUserEntity)
                .availableAmount(BigDecimal.ZERO)
                .accountedAmount(BigDecimal.ZERO)
                .interestRate(BigDecimal.valueOf(3))
                .riskLevel(AccountEntity.RiskLevel.LOW)
                .accountNumber(UUID.randomUUID().toString().replace("-", ""))
                .build();
        return savingAccountMapper.toDTO(
                savingAccountRepository.save(savingAccountEntity)
        );
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void closeAccount(String accountNumber) {
        log.info("Closing saving account number: {}", accountNumber);
        final SavingAccountEntity savingAccountEntity = savingAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoSavingAccountFoundException(
                        String.format("No saving account found with account number %s", accountNumber))
                );
        createClosedSavingAccount(savingAccountEntity);
        savingAccountRepository.deleteByAccountNumber(accountNumber);
    }

    private void createClosedSavingAccount(final SavingAccountEntity savingAccountEntity) {
        final ClosedSavingAccountEntity closedSavingAccountEntity = ClosedSavingAccountEntity.builder().build();
        BeanUtils.copyProperties(savingAccountEntity, closedSavingAccountEntity);
        closedSavingAccountEntity.setId(null);
        closedSavingAccountEntity.setLastAvailableAmount(savingAccountEntity.getAvailableAmount());
        closedSavingAccountEntity.setLastAccountedAmount(savingAccountEntity.getAccountedAmount());
        closedSavingAccountEntity.setLastInterestRate(savingAccountEntity.getInterestRate());
        closedSavingAccountEntity.setClosingTimestamp(LocalDateTime.now(ZoneId.of("Europe/Paris")));
        closedSavingAccountRepository.save(closedSavingAccountEntity);
    }

}
