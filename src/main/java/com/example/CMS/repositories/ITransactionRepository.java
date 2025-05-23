package com.example.CMS.repositories;

import com.example.CMS.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionModel, UUID> {
}
