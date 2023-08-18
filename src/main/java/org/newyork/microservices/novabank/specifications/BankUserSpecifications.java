package org.newyork.microservices.novabank.specifications;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.newyork.microservices.novabank.entities.BankUserEntity;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankUserSpecifications {

    public static Specification<BankUserEntity> firstNameContains(final String search) {
        return (bankUser, cq, cb) -> cb.like(bankUser.get("firstName"), "%" + search + "%");
    }

    public static Specification<BankUserEntity> lastNameContains(final String search) {
        return (bankUser, cq, cb) -> cb.like(bankUser.get("lastName"), "%" + search + "%");
    }

    public static Specification<BankUserEntity> emailContains(final String search) {
        return (bankUser, cq, cb) -> cb.like(bankUser.get("email"), "%" + search + "%");
    }

    public static Specification<BankUserEntity> userAccountNumberContains(final String search) {
        return (bankUser, cq, cb) -> cb.like(bankUser.get("userAccountNumber"), "%" + search + "%");
    }

}
