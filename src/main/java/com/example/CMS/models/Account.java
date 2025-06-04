package com.example.CMS.models;

import com.example.CMS.models.enums.CurrencyType;
import com.example.CMS.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "account")
@EqualsAndHashCode(exclude = "cards")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(unique = false)
    private CurrencyType currency;

    private BigDecimal balance;
    @ManyToMany
    @JoinTable(
            name = "account_card",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards;
}