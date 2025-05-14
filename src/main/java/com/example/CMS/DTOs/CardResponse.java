package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CardResponse {
    private UUID id;
    private Status status;
    private String cardNumber;
    private LocalDate expiry;
}