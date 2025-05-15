package com.example.CMS.controllers;

import com.example.CMS.dtos.TransactionRequest;
import com.example.CMS.dtos.TransactionResponse;
import com.example.CMS.models.TransactionModel;
import com.example.CMS.services.TransactionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest requestDTO) {
        try {
            TransactionModel newTransaction = transactionService.createTransaction(
                    requestDTO.getCardId(),
                    requestDTO.getTransactionAmount(),
                    requestDTO.getTransactionType(),
                    requestDTO.getCurrency()
            );


            TransactionResponse response = modelMapper.map(newTransaction, TransactionResponse.class);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unhandled exception occurred:");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }
}
