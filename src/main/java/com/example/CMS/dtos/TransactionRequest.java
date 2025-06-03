package com.example.CMS.dtos;

import com.example.CMS.models.enums.CurrencyType;
import com.example.CMS.models.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "card number is required")
    private String cardNumber;
    @NotNull(message = "transaction amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "transaction amount must be positive")
    private BigDecimal transactionAmount;
    @NotNull(message = "transaction type is required")
    private TransactionType transactionType;
    @NotNull(message = "currency is required")
    private CurrencyType currency;

}
