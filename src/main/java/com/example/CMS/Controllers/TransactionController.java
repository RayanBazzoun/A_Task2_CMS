package com.example.CMS.Controllers;

import com.example.CMS.DTOs.TransactionDTO;
import com.example.CMS.Models.TransactionModel;
import com.example.CMS.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO request) {
        try {
            TransactionModel newTransaction = transactionService.createTransaction(
                    request.getCardId(),
                    request.getTransactionAmount(),
                    request.getTransactionType()
            );
            return ResponseEntity.ok(newTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Transaction failed: " + e.getMessage());
        }
    }

}
