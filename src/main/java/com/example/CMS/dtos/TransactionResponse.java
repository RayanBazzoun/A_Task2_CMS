package com.example.CMS.dtos;

import com.example.CMS.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class TransactionResponse {
    private UUID id;
    private UUID cardId;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
}