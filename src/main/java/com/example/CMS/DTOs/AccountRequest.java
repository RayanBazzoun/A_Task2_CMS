package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountRequest {
    private UUID accountId;
    private Status status;
}
