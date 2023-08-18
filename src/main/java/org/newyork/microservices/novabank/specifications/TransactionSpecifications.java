package org.newyork.microservices.novabank.specifications;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionSpecifications {

    public static Specification<CheckingAccountTransactionEntity> startDateAfter(final LocalDateTime startDateTime) {
        return (checkingAccountTransaction, cq, cb) -> cb.greaterThanOrEqualTo(
                checkingAccountTransaction.get("operationDate"), startDateTime
        );
    }

    public static Specification<CheckingAccountTransactionEntity> endDateBefore(final LocalDateTime endDateTime) {
        return (checkingAccountTransaction, cq, cb) -> cb.lessThanOrEqualTo(
                checkingAccountTransaction.get("operationDate"), endDateTime
        );
    }

    public static Specification<CheckingAccountTransactionEntity> betweenStartDateAndEndDate(
            final LocalDateTime startDateTime, final LocalDateTime endDateTime
    ) {
        return (checkingAccountTransaction, cq, cb) -> cb.between(
                checkingAccountTransaction.get("operationDate"), startDateTime, endDateTime
        );
    }

    public static Specification<CheckingAccountTransactionEntity> accountNumberEquals(final String accountNumber) {
        return (checkingAccountTransaction, cq, cb) -> cb.equal(
                checkingAccountTransaction.get("account").get("accountNumber"), accountNumber
        );
    }

}
