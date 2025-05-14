package com.example.CMS.DTOs;

import java.util.UUID;
import com.example.CMS.Models.enums.Status;
import lombok.Data;

@Data
public class CardRequest {
    private UUID cardId;
    private Status status;
}
