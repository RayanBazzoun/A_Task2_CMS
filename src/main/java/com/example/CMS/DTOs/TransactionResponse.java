package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionResponse {
    private UUID id;
    private UUID cardId;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
}