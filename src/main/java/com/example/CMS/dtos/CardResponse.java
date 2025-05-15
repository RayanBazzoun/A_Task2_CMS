package com.example.CMS.dtos;

import com.example.CMS.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponse {
    private UUID id;
    private Status status;
    private String cardNumber;
    private LocalDate expiry;
}