package com.example.CMS.DTOs;

import java.util.UUID;
import com.example.CMS.Models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CardRequest {
    private UUID cardId;
    private Status status;
}
