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
@Table(name = "saving_account_transactions")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SavingAccountTransactionEntity extends TransactionEntity {

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "saving_account_id")
    private SavingAccountEntity account;

}
