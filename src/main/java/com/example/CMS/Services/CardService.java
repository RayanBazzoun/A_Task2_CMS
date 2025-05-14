package com.example.CMS.Services;

import com.example.CMS.DTOs.CardRequest;
import com.example.CMS.Models.AccountModel;
import com.example.CMS.Models.CardModel;
import com.example.CMS.Models.enums.Status;
import com.example.CMS.Repositories.IAccountRepository;
import com.example.CMS.Repositories.ICardRepository;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private ICardRepository cardRepository;
    @Autowired
    private IAccountRepository accountRepository;

    private final AES256TextEncryptor encryptor;

    public CardService() {
        this.encryptor = new AES256TextEncryptor();
        this.encryptor.setPassword("RayanBazzoun");
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    public CardModel createCard(UUID accountId) {
        AccountModel account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        CardModel card = new CardModel();
        card.setCardNumber(encryptCardNumber(generateCardNumber()));
        card.setStatus(Status.ACTIVE); // Use enum
        card.setExpiry(LocalDate.now().plusYears(5));
        card.getAccounts().add(account);

        return cardRepository.save(card);
    }

    public CardModel updateCardStatus(CardRequest cardRequest) {
        CardModel card = cardRepository.findById(cardRequest.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        card.setStatus(cardRequest.getStatus()); // Use enum directly
        return cardRepository.save(card);
    }

    public CardModel getCardDetails(UUID cardId) {
        CardModel card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        card.setCardNumber(decryptCardNumber(card.getCardNumber())); // Decrypt card number before returning
        return card;
    }

    public List<CardModel> getCardsByAccId(UUID accountId) {
        AccountModel account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getCards().stream().toList();
    }

    private String encryptCardNumber(String cardNumber) {
        return encryptor.encrypt(cardNumber);
    }

    private String decryptCardNumber(String encryptedCardNumber) {
        return encryptor.decrypt(encryptedCardNumber);
    }
}