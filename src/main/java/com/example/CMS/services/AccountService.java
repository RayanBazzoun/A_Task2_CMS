package com.example.CMS.services;

import com.example.CMS.dtos.CreateAccountRequest;
import com.example.CMS.dtos.UpdateAccountRequest;
import com.example.CMS.models.Account;
import com.example.CMS.models.enums.Status;
import com.example.CMS.repositories.IAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Account createAccount(CreateAccountRequest request) {
        Account account = modelMapper.map(request, Account.class);
        account.setStatus(Status.ACTIVE);
        return accountRepository.save(account);

    }


    public Account updateAccount(UpdateAccountRequest updateAccountRequest) {
        // Find account by ID
        Account account = accountRepository.findById(updateAccountRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Update the account's status
        account.setStatus(updateAccountRequest.getStatus());
        return accountRepository.save(account);
    }

    public boolean deleteAccount(UUID id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}