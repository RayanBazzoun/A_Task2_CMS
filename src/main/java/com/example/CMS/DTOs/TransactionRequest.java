package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public class TransactionRequest {
    private UUID cardId;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
}
