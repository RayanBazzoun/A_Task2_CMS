package com.example.CMS.Models;
import com.example.CMS.Models.enums.TransactionType;
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
public class TransactionModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardModel card;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionDate;

    private BigDecimal transactionAmount;
}