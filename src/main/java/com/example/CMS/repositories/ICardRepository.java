package com.example.CMS.repositories;
import com.example.CMS.models.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ICardRepository extends JpaRepository<CardModel, UUID> {

    List<CardModel> findAllByAccounts_Id(UUID accountId);
}
