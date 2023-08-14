package org.newyork.microservices.novabank.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checking_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@ToString(exclude = {"transactions"})
public class CheckingAccountEntity extends AccountEntity {

    @Column(name = "available_amount")
    private BigDecimal availableAmount;

    @Column(name = "accounted_amount")
    private BigDecimal accountedAmount;

    @Column(name = "authorized_overdraft_amount")
    private BigDecimal authorizedOverdraftAmount;

    @OneToMany(
         mappedBy = "account",
         fetch = FetchType.LAZY,
         cascade = {
                 CascadeType.DETACH,
                 CascadeType.MERGE,
                 CascadeType.PERSIST,
                 CascadeType.REFRESH
         }
    )
    private List<CheckingAccountTransactionEntity> transactions;

}
