package com.example.CMS.dtos;

import com.example.CMS.models.enums.CurrencyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateAccountRequest {
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "100.0", inclusive = true, message = "cant create an account with a balance less than 100")
    private BigDecimal balance;
    @NotNull(message = "Currency is required")
    private CurrencyType currency;
}
