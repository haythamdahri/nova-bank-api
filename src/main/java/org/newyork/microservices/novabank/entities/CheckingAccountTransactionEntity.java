package org.newyork.microservices.novabank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "checking_account_transactions")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CheckingAccountTransactionEntity extends TransactionEntity {

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "checking_account_id")
    private CheckingAccountEntity account;

}
