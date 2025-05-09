package com.example.CMS.Services;

import com.example.CMS.Models.AccountModel;
import com.example.CMS.Repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;
    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
    public AccountModel createAccount(BigDecimal balance){
        AccountModel accountModel=new AccountModel();
        accountModel.setStatus("ACTIVE");
        accountModel.setBalance(balance);
        return accountRepository.save(accountModel);
    }
    public AccountModel updateAccount(UUID id,String status){
        Optional<AccountModel> account = accountRepository.findById(id);
        if(account.isPresent()){
            AccountModel acc= account.get();
            String normalizedStatus= status.trim().toUpperCase();
            if (normalizedStatus.equals("ACTIVE") || normalizedStatus.equals("INACTIVE")) {
                acc.setStatus(normalizedStatus);
            } else {
                throw new IllegalArgumentException("Status must be either ACTIVE or INACTIVE");
            }

            return accountRepository.save(acc);
        }
        return null;
    }
    public boolean deleteAccount(UUID id){
        Optional<AccountModel> account = accountRepository.findById(id);
        if(account.isPresent()){
            AccountModel acc=account.get();
             accountRepository.delete(acc);
            return true;
        }
        return false;
    }

    public AccountModel getAccount(UUID id){
        Optional<AccountModel> account = accountRepository.findById(id);
        if(account.isPresent()){
            AccountModel acc=account.get();
            return acc;
        }
        return null;
    }
    public List<AccountModel> getAllAccounts(){
        List<AccountModel> account = accountRepository.findAll();
        return account;
    }
    public AccountModel saveAccount(AccountModel account) {
        return accountRepository.save(account);
    }

}
