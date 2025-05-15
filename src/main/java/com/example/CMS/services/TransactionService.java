package com.example.CMS.services;

import com.example.CMS.models.AccountModel;
import com.example.CMS.models.CardModel;
import com.example.CMS.models.TransactionModel;
import com.example.CMS.models.enums.CurrencyType;
import com.example.CMS.models.enums.Status;
import com.example.CMS.models.enums.TransactionType;
import com.example.CMS.repositories.ITransactionRepository;
import com.example.CMS.repositories.IAccountRepository;
import com.example.CMS.repositories.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICardRepository cardRepository;

    public TransactionModel createTransaction(UUID cardId, BigDecimal transactionAmount, TransactionType transactionType, CurrencyType currency) {
        CardModel card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if (card.getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("Card is not active");
        }

        if (card.getExpiry().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Card is expired");
        }

        AccountModel account = card.getAccounts()
                .stream()
                .filter(acc -> acc.getCurrency() == currency)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account with currency " + currency + " not found for card"));

        if (account.getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("Account is not active");
        }

        if (transactionType == TransactionType.DEBIT) {
            if (account.getBalance().compareTo(transactionAmount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(transactionAmount));
        } else if (transactionType == TransactionType.CREDIT) {
            account.setBalance(account.getBalance().add(transactionAmount));
        }

        accountRepository.save(account);

        TransactionModel transaction = TransactionModel.builder()
                .card(card)
                .transactionType(transactionType)
                .transactionAmount(transactionAmount)
                .transactionDate(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);

    }
}