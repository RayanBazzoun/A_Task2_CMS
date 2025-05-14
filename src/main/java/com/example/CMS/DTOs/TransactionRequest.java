package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private UUID cardId;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
}
