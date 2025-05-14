package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {
    private UUID id;
    private Status status;
    private BigDecimal balance;
}