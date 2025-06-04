package com.example.CMS.controllers;

import com.example.CMS.dtos.CardRequest;
import com.example.CMS.dtos.CardResponse;
import com.example.CMS.models.Card;
import com.example.CMS.services.CardService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CardResponse> createCard(@RequestParam UUID accountId) {
        Card createdCard = cardService.createCard(accountId);
        CardResponse response = modelMapper.map(createdCard, CardResponse.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<CardResponse> updateCardStatus(@Valid @RequestBody CardRequest cardRequest) {
        Card updatedCard = cardService.updateCardStatus(cardRequest);
        if (updatedCard == null) {
            return ResponseEntity.notFound().build();
        }
        CardResponse response = modelMapper.map(updatedCard, CardResponse.class);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCardDetails(@PathVariable UUID cardId) {
        Card card = cardService.getCardDetails(cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        CardResponse response = modelMapper.map(card, CardResponse.class);
        return ResponseEntity.ok(response);
    }

}
