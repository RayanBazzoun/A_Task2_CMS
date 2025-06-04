package com.example.CMS.repositories;
import com.example.CMS.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ICardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByCardNumber(String cardNumber);
}

