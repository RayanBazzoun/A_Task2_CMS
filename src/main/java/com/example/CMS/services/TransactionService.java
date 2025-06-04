package com.example.CMS.services;

import com.example.CMS.dtos.TransactionResponse;
import com.example.CMS.models.Account;
import com.example.CMS.models.Card;
import com.example.CMS.models.Transaction;
import com.example.CMS.models.enums.CurrencyType;
import com.example.CMS.models.enums.Status;
import com.example.CMS.models.enums.TransactionType;
import com.example.CMS.repositories.ITransactionRepository;
import com.example.CMS.repositories.IAccountRepository;
import com.example.CMS.repositories.ICardRepository;
import com.example.CMS.utils.DeterministicAesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICardRepository cardRepository;

    private final DeterministicAesUtil aesUtil;

    public TransactionService() {
        String key = System.getenv("CARD_AES_KEY");
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("CARD_AES_KEY environment variable not set.");
        }
        this.aesUtil = new DeterministicAesUtil(key);
    }

    public TransactionResponse createTransaction(String cardNumber, BigDecimal transactionAmount, TransactionType transactionType, CurrencyType currency) {
        try {
            String encryptedCardNumber = aesUtil.encrypt(cardNumber);

            Optional<Card> optionalCard = cardRepository.findAll().stream()
                    .filter(c -> c.getCardNumber().equals(encryptedCardNumber))
                    .findFirst();

            if (optionalCard.isEmpty()) {
                return TransactionResponse.builder()
                        .success(false)
                        .message("Card not found")
                        .build();
            }

            Card card = optionalCard.get();

            if (card.getStatus() != Status.ACTIVE) {
                throw new IllegalArgumentException("Card is not active");
            }

            if (card.getExpiry().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Card is expired");
            }

            Account account = card.getAccounts().stream()
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

            Transaction transaction = Transaction.builder()
                    .card(card)
                    .transactionType(transactionType)
                    .transactionAmount(transactionAmount)
                    .transactionDate(LocalDateTime.now())
                    .build();

            Transaction savedTransaction = transactionRepository.save(transaction);

            return TransactionResponse.builder()
                    .success(true)
                    .message("Transaction completed successfully")
                    .transactionId(savedTransaction.getId().toString())
                    .status(transactionType.name())
                    .build();

        } catch (IllegalArgumentException e) {
            return TransactionResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return TransactionResponse.builder()
                    .success(false)
                    .message("Unexpected error occurred")
                    .build();
        }
    }
}
