package com.example.CMS.Controllers;

import com.example.CMS.Models.CardModel;
import com.example.CMS.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController

@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<CardModel> createCard(@RequestBody UUID accountId) {
        CardModel createdCard = cardService.createCard(accountId);
        return ResponseEntity.ok(createdCard);
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<CardModel> updateCardStatus(@PathVariable UUID cardId,
                                                      @RequestBody String status) {
        System.out.println("Received status: " + status);

        CardModel updatedCard = cardService.updateCardStatus(cardId, status);
        return updatedCard != null ? ResponseEntity.ok(updatedCard) : ResponseEntity.notFound().build();
    }


    @GetMapping("/{cardId}")
    public ResponseEntity<CardModel> getCardDetails(@PathVariable UUID cardId) {
        CardModel card = cardService.getCardDetails(cardId);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.notFound().build();
    }

}
