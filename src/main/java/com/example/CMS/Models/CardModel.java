package com.example.CMS.Models;
import com.example.CMS.Models.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "card")
public class CardModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate expiry;

    private String cardNumber;

    @ManyToMany(mappedBy = "cards")
    private Set<AccountModel> accounts = new HashSet<>();
}