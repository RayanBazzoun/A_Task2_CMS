package com.example.CMS.Controllers;

import com.example.CMS.Models.AccountModel;
import com.example.CMS.Models.CardModel;
import com.example.CMS.Services.AccountService;
import com.example.CMS.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")

public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CardService cardService;
    @PostMapping
    public ResponseEntity<AccountModel>createAccount(@RequestBody BigDecimal balance){
        AccountModel newAcc= accountService.createAccount(balance);
        return ResponseEntity.ok(newAcc);
    }

    @PutMapping("/{accountId}/status")
    public ResponseEntity<AccountModel> updateCardStatus(@PathVariable UUID accountId,
                                                      @RequestBody String status) {
        System.out.println("Received status: " + status);

        AccountModel updateAccount = accountService.updateAccount(accountId,status);
        return updateAccount != null ? ResponseEntity.ok(updateAccount) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        boolean deleted = accountService.deleteAccount(accountId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountModel>getAccount(@PathVariable UUID accountId){
        AccountModel acc= accountService.getAccount(accountId);
        return acc != null ? ResponseEntity.ok(acc) : ResponseEntity.notFound().build();
    }
    @GetMapping
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        List<AccountModel> acc = accountService.getAllAccounts();
        return acc != null && !acc.isEmpty() ? ResponseEntity.ok(acc) : ResponseEntity.noContent().build();
    }
    @GetMapping("/cards/{accountID}")
    public ResponseEntity<List<CardModel>>getCards(@PathVariable UUID accountID){
        return ResponseEntity.ok(cardService.getCardsByAccId(accountID));
    }
}
