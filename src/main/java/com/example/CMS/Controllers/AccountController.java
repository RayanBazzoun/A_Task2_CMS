package com.example.CMS.Controllers;

import com.example.CMS.DTOs.AccountRequest;
import com.example.CMS.DTOs.AccountResponse;
import com.example.CMS.Models.AccountModel;
import com.example.CMS.Services.AccountService;
import com.example.CMS.Services.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody BigDecimal balance) {
        AccountModel newAccount = accountService.createAccount(balance);
        AccountResponse response = modelMapper.map(newAccount, AccountResponse.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{accountId}/status")
    public ResponseEntity<AccountResponse> updateAccountStatus(@RequestBody AccountRequest accountRequest) {
        AccountModel updatedAccount = accountService.updateAccount(accountRequest);
        if (updatedAccount == null) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = modelMapper.map(updatedAccount, AccountResponse.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        boolean deleted = accountService.deleteAccount(accountId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID accountId) {
        AccountModel account = accountService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = modelMapper.map(account, AccountResponse.class);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountModel> accounts = accountService.getAllAccounts();
        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<AccountResponse> response = accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}