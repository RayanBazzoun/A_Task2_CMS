package com.example.CMS.Services;

import com.example.CMS.Models.AccountModel;
import com.example.CMS.Models.CardModel;
import com.example.CMS.Models.TransactionModel;
import com.example.CMS.Repositories.ITransactionRepository;
import com.example.CMS.Repositories.IAccountRepository;
import com.example.CMS.Repositories.ICardRepository;
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

    public TransactionModel createTransaction(UUID cardId, BigDecimal transactionAmount, String transactionType) {
        CardModel card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if (!card.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new IllegalArgumentException("Card is not active");
        }

        if (card.getExpiry().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Card is expired");
        }

        AccountModel account = card.getAccount();

        if (!account.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new IllegalArgumentException("Account is not active");
        }

        if (transactionType.equalsIgnoreCase("DEBIT")) {
            if (account.getBalance().compareTo(transactionAmount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(transactionAmount));
        } else if (transactionType.equalsIgnoreCase("CREDIT")) {
            account.setBalance(account.getBalance().add(transactionAmount));
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        accountRepository.save(account);

        TransactionModel transaction = new TransactionModel();
        transaction.setCard(card);
        transaction.setTransaction_type(transactionType.toUpperCase());
        transaction.setTransaction_amount(transactionAmount);
        transaction.setTransaction_date(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
