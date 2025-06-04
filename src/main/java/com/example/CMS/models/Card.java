package com.example.CMS.models;
import com.example.CMS.models.enums.Status;
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
@EqualsAndHashCode(exclude = "accounts")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate expiry;

    @Column(unique = true)
    private String cardNumber;

    @ManyToMany(mappedBy = "cards")
    private Set<Account> accounts = new HashSet<>();
}