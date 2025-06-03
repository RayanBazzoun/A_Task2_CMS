package com.example.CMS.controllers;

import com.example.CMS.dtos.TransactionRequest;
import com.example.CMS.dtos.TransactionResponse;
import com.example.CMS.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest requestDTO) {
        try {
            TransactionResponse response = transactionService.createTransaction(
                    requestDTO.getCardNumber(),
                    requestDTO.getTransactionAmount(),
                    requestDTO.getTransactionType(),
                    requestDTO.getCurrency()
            );

            if (!response.isSuccess()) {
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Unhandled exception occurred:");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }
}
