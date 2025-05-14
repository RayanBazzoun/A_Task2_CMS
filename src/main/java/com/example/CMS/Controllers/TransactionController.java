package com.example.CMS.Controllers;

import com.example.CMS.DTOs.TransactionRequest;
import com.example.CMS.DTOs.TransactionResponse;
import com.example.CMS.Models.TransactionModel;
import com.example.CMS.Services.TransactionService;
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
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest requestDTO) {
        try {
            TransactionModel newTransaction = transactionService.createTransaction(
                    requestDTO.getCardId(),
                    requestDTO.getTransactionAmount(),
                    requestDTO.getTransactionType()
            );
            TransactionResponse response = modelMapper.map(newTransaction, TransactionResponse.class);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}