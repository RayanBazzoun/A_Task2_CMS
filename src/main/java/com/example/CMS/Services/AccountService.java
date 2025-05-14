package com.example.CMS.Services;

import com.example.CMS.DTOs.AccountRequest;
import com.example.CMS.Models.AccountModel;
import com.example.CMS.Models.enums.Status;
import com.example.CMS.Repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;

    public AccountModel createAccount(BigDecimal balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setStatus(Status.ACTIVE); // Use enum
        accountModel.setBalance(balance);
        return accountRepository.save(accountModel);
    }

    public AccountModel updateAccount(AccountRequest accountRequest) {
        // Find account by ID
        AccountModel account = accountRepository.findById(accountRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Update the account's status
        account.setStatus(accountRequest.getStatus()); // Use enum directly
        return accountRepository.save(account);
    }

    public boolean deleteAccount(UUID id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public AccountModel getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountModel saveAccount(AccountModel account) {
        return accountRepository.save(account);
    }
}