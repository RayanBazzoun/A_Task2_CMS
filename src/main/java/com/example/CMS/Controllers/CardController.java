package com.example.CMS.Controllers;

import com.example.CMS.DTOs.CardRequest;
import com.example.CMS.DTOs.CardResponse;
import com.example.CMS.Models.CardModel;
import com.example.CMS.Services.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController

@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody UUID accountId) {
        CardModel createdCard = cardService.createCard(accountId);
        CardResponse response = modelMapper.map(createdCard, CardResponse.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<CardResponse> updateCardStatus(@RequestBody CardRequest cardRequest) {
        CardModel updatedCard = cardService.updateCardStatus(cardRequest);
        if (updatedCard == null) {
            return ResponseEntity.notFound().build();
        }
        CardResponse response = modelMapper.map(updatedCard, CardResponse.class);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCardDetails(@PathVariable UUID cardId) {
        CardModel card = cardService.getCardDetails(cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        // Map Model to ResponseDTO
        CardResponse response = modelMapper.map(card, CardResponse.class);
        return ResponseEntity.ok(response);
    }

}
