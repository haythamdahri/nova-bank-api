package org.newyork.microservices.novabank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "closed_saving_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
public class ClosedSavingAccountEntity extends AccountEntity {

    @Column(name = "last_available_amount")
    private BigDecimal lastAvailableAmount;

    @Column(name = "last_accounted_amount")
    private BigDecimal lastAccountedAmount;

    @Column(name = "last_interest_rate")
    private BigDecimal lastInterestRate;

    @Column(name = "closing_timestamp")
    private LocalDateTime closingTimestamp;

}
