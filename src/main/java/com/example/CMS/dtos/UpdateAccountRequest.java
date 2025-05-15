package com.example.CMS.dtos;

import com.example.CMS.models.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateAccountRequest {
    @NotNull(message = "Account ID is required")
    private UUID accountId;
    @NotNull(message = "status is required")
    private Status status;
}
