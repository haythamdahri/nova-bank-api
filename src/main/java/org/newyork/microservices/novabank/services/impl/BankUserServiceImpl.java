package org.newyork.microservices.novabank.services.impl;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.newyork.microservices.novabank.dao.BankUserRepository;
import org.newyork.microservices.novabank.dto.BankUserDTO;
import org.newyork.microservices.novabank.dto.CreateBankUserRequestDTO;
import org.newyork.microservices.novabank.dto.ResetBankUserPasswordRequestDTO;
import org.newyork.microservices.novabank.dto.UpdateBankUserRequestDTO;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.newyork.microservices.novabank.exceptions.BankUserExistsException;
import org.newyork.microservices.novabank.exceptions.BusinessException;
import org.newyork.microservices.novabank.exceptions.NoBankUserFoundException;
import org.newyork.microservices.novabank.mappers.BankUserMapper;
import org.newyork.microservices.novabank.mappers.CreateBankUserMapper;
import org.newyork.microservices.novabank.services.BankUserService;
import org.newyork.microservices.novabank.services.CheckingAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.newyork.microservices.novabank.specifications.BankUserSpecifications.*;

@Service
@Log4j2
public class BankUserServiceImpl implements BankUserService {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    );

    private final BankUserRepository bankUserRepository;
    private final CreateBankUserMapper createBankUserMapper;
    private final BankUserMapper bankUserMapper;
    private final CheckingAccountService checkingAccountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public BankUserServiceImpl(
            final BankUserRepository bankUserRepository,
            final CreateBankUserMapper createBankUserMapper,
            final CheckingAccountService checkingAccountService,
            final BankUserMapper bankUserMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bankUserRepository = bankUserRepository;
        this.createBankUserMapper = createBankUserMapper;
        this.checkingAccountService = checkingAccountService;
        this.bankUserMapper = bankUserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = {SQLException.class})
    public BankUserDTO createBankUser(final CreateBankUserRequestDTO createBankUserRequest) {
        log.info("New request to create bank user: {}", createBankUserRequest);
        validateBankUserCreationRequest(createBankUserRequest);
        BankUserEntity bankUserEntity = createBankUserMapper.toEntity(createBankUserRequest);
        bankUserEntity.setUserAccountNumber(UUID.randomUUID().toString().replace("-", ""));
        final BankUserEntity bankUser = bankUserRepository.save(bankUserEntity);
        checkingAccountService.createCheckingAccount(bankUser, createBankUserRequest);
        // TODO: Send email to user to reset password
        return bankUserMapper.toDTO(bankUser);
    }

    @Override
    public BankUserDTO updateBankUser(final UpdateBankUserRequestDTO updateBankUserRequest) {
        log.info("New request to update bank user: {}", updateBankUserRequest);
        final BankUserEntity bankUser = this.getBankUserEntity(updateBankUserRequest.getUserAccountNumber());
        BeanUtils.copyProperties(updateBankUserRequest, bankUser);
        return bankUserMapper.toDTO(bankUserRepository.save(bankUser));
    }

    @Override
    public BankUserEntity getBankUserEntity(final String userAccountNumber) {
        return bankUserRepository.findByUserAccountNumber(userAccountNumber)
                .orElseThrow(() -> new NoBankUserFoundException(
                        String.format("No bank user found with user account number %s", userAccountNumber)
                ));
    }

    @Override
    public BankUserDTO getBankUser(final Long id) {
        return bankUserRepository.findById(id).map(bankUserMapper::toDTO)
                .orElseThrow(() -> new NoBankUserFoundException(String.format("No bank user found with id %d", id)));
    }

    @Override
    public BankUserDTO getBankUserByUserAccountNumber(final String userAccountNumber) {
        return bankUserRepository.findByUserAccountNumber(userAccountNumber)
                .map(bankUserMapper::toDTO)
                .orElseThrow(() -> new NoBankUserFoundException(
                        String.format("No bank user found with user account number %s", userAccountNumber))
                );
    }

    @Override
    public List<BankUserDTO> getBankUsers() {
        return bankUserMapper.toDTO(IterableUtils.toList(bankUserRepository.findAll()));
    }

    @Override
    public List<BankUserDTO> searchBankUsers(String search) {
        return bankUserMapper.toDTO(
                IterableUtils.toList(
                        bankUserRepository.findAll(
                                Specification.where(firstNameContains(search))
                                        .or(lastNameContains(search))
                                        .or(emailContains(search))
                                        .or(userAccountNumberContains(search))
                        )
                )
        );
    }

    @Override
    public void resetPassword(final ResetBankUserPasswordRequestDTO resetBankUserPasswordRequest) {
        log.info("Resetting bank user {} password", resetBankUserPasswordRequest.getUserAccountNumber());
        final int updatedBankUsers = bankUserRepository.updateUserPassword(
                resetBankUserPasswordRequest.getUserAccountNumber(),
                bCryptPasswordEncoder.encode(Arrays.toString(resetBankUserPasswordRequest.getPassword()))
        );
        Arrays.fill(resetBankUserPasswordRequest.getPassword(), null);
        if (updatedBankUsers == 0) {
            throw new BankUserExistsException(
                    String.format(
                            "Password update failed for bank user %s",
                            resetBankUserPasswordRequest.getUserAccountNumber())
            );
        }
    }

    private void validateBankUserCreationRequest(final CreateBankUserRequestDTO createBankUserRequest) {
        if (bankUserRepository.existsByEmail(createBankUserRequest.getEmail())) {
            throw new BankUserExistsException(
                    String.format("Bank user with email %s already exists", createBankUserRequest.getEmail())
            );
        }
//        if (!PASSWORD_PATTERN.matcher(createBankUserRequest.getPassword()).matches()) {
//            throw new BusinessException(
//                    "Provided password does not meet minimum requirements [Uppercase, lowercase, numbers and special character]"
//            );
//        }
    }

}
