package org.newyork.microservices.novabank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "bank_user", nullable = false)
    private BankUserEntity bankUser;

    public enum RiskLevel {
        LOW(1000), AVERAGE(500), HIGH(300), RISKY(100);

        private final int initialAmount;

        RiskLevel(final int initialAmount) {
            this.initialAmount = initialAmount;
        }

        public BigDecimal getAuthorizedOverdraftAmount() {
            return BigDecimal.valueOf(this.initialAmount);
        }
    }

}
