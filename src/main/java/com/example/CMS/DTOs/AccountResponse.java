package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountResponse {
    private UUID id;
    private Status status;
    private BigDecimal balance;
}