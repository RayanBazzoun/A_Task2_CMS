package com.example.CMS.models;
import com.example.CMS.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionDate;

    private BigDecimal transactionAmount;
}