package com.example.CMS.dtos;

import java.util.UUID;
import com.example.CMS.models.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CardRequest {
    @NotNull(message = "card ID is required")
    private UUID cardId;
    @NotNull(message = "status is required")
    private Status status;
}
