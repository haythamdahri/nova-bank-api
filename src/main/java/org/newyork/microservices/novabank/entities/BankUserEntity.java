package org.newyork.microservices.novabank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_user")
@SuperBuilder(toBuilder = true)
public class BankUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_account_number")
    private String userAccountNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    @Column(name = "job")
    private String job;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            mappedBy = "bankUser"
    )
    private List<CheckingAccountEntity> checkingAccounts;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            mappedBy = "bankUser"
    )
    private List<SavingAccountEntity> savingAccounts;

}
