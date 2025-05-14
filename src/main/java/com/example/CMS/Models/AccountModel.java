package com.example.CMS.Models;

import com.example.CMS.Models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "account")
public class AccountModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal balance;
    @ManyToMany
    @JoinTable(
            name = "account_card",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<CardModel> cards;
}