package com.example.CMS.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDTO {
    private UUID cardId;
    private BigDecimal transactionAmount;
    private String transactionType;
    public UUID getCardId() {
        return cardId;
    }
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
