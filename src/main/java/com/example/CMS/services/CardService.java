package com.example.CMS.services;

import com.example.CMS.dtos.CardRequest;
import com.example.CMS.models.Account;
import com.example.CMS.models.Card;
import com.example.CMS.models.enums.Status;
import com.example.CMS.repositories.IAccountRepository;
import com.example.CMS.repositories.ICardRepository;
import com.example.CMS.utils.DeterministicAesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private IAccountRepository accountRepository;

    private final DeterministicAesUtil aesUtil;

    public CardService() {
        String key = System.getenv("CARD_AES_KEY");
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("CARD_AES_KEY environment variable not set.");
        }
        this.aesUtil = new DeterministicAesUtil(key);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    public Card createCard(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        String plainCardNumber = generateCardNumber();
        String encryptedCardNumber;
        try {
            encryptedCardNumber = aesUtil.encrypt(plainCardNumber);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }

        Card card = new Card();
        card.setCardNumber(encryptedCardNumber);
        card.setStatus(Status.ACTIVE);
        card.setExpiry(LocalDate.now().plusYears(5));

        if (card.getAccounts() == null) {
            card.setAccounts(new HashSet<>());
        }
        card.getAccounts().add(account);

        if (account.getCards() == null) {
            account.setCards(new HashSet<>());
        }
        account.getCards().add(card);

        cardRepository.save(card);
        accountRepository.save(account);

        // Optionally return card with plain number shown
        card.setCardNumber(plainCardNumber);
        return card;
    }

    public Card updateCardStatus(CardRequest cardRequest) {
        Card card = cardRepository.findById(cardRequest.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        card.setStatus(cardRequest.getStatus());
        return cardRepository.save(card);
    }

    public Card getCardDetails(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        try {
            card.setCardNumber(aesUtil.decrypt(card.getCardNumber()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt card number", e);
        }

        return card;
    }

    public List<Card> getCardsByAccId(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getCards().stream().toList();
    }
}
