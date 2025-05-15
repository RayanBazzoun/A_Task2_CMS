package com.example.CMS.services;

import com.example.CMS.dtos.CreateAccountRequest;
import com.example.CMS.dtos.UpdateAccountRequest;
import com.example.CMS.models.AccountModel;
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
    public AccountModel createAccount(CreateAccountRequest request) {
        AccountModel account = modelMapper.map(request, AccountModel.class);
        account.setStatus(Status.ACTIVE);
        return accountRepository.save(account);

    }


    public AccountModel updateAccount(UpdateAccountRequest updateAccountRequest) {
        // Find account by ID
        AccountModel account = accountRepository.findById(updateAccountRequest.getAccountId())
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