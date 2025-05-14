package com.example.CMS.DTOs;

import com.example.CMS.Models.enums.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountRequest {
    private UUID accountId;
    private Status status;
}
