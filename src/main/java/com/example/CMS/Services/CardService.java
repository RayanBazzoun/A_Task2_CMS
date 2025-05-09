package com.example.CMS.Services;

import com.example.CMS.Models.AccountModel;
import com.example.CMS.Models.CardModel;
import com.example.CMS.Repositories.IAccountRepository;
import com.example.CMS.Repositories.ICardRepository;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private ICardRepository cardRepository;
    @Autowired
    private IAccountRepository accountRepository;
    private AES256TextEncryptor encryptor;

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

    // Create a new card
    public CardModel createCard(UUID accountId) {
        Optional<AccountModel> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        String rawCardNumber = generateCardNumber();
        String encryptedCardNumber = encryptCardNumber(rawCardNumber);

        CardModel card = new CardModel();
        card.setCardnumber(encryptedCardNumber);
        card.setStatus("ACTIVE");
        card.setExpiry(LocalDate.now().plusYears(5));
        card.setAccount(accountOpt.get());

        return cardRepository.save(card);
    }


    public CardModel updateCardStatus(UUID cardId, String status) {
        Optional<CardModel> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isPresent()) {
            CardModel card = cardOpt.get();

            String normalizedStatus = status.trim().toUpperCase();

            if (normalizedStatus.equals("ACTIVE") || normalizedStatus.equals("INACTIVE")) {
                card.setStatus(normalizedStatus);
            } else {
                throw new IllegalArgumentException("Status must be either ACTIVE or INACTIVE");
            }

            return cardRepository.save(card);
        }
        return null;
    }


    public CardModel getCardDetails(UUID cardId) {
        Optional<CardModel> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isPresent()) {
            CardModel card = cardOpt.get();
            card.setCardnumber(decryptCardNumber(card.getCardnumber()));
            return card;
        }
        return null;
    }

    public List<CardModel> getCardsByAccId(UUID accountID) {
        return cardRepository.findAllByAccount_Id(accountID);
    }

    private String encryptCardNumber(String cardNumber) {
        return encryptor.encrypt(cardNumber);
    }

    private String decryptCardNumber(String encryptedCardNumber) {
        return encryptor.decrypt(encryptedCardNumber);
    }
}
