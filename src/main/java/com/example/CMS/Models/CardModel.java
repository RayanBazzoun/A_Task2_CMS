package com.example.CMS.Models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
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
    private String status;


    private LocalDate expiry;

    private String cardnumber;



    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountModel account;
    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }
    public UUID getId() {
        return id;
    }
    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }


    public String getStatus() {
        return status;
    }
    public LocalDate getExpiry() {
        return expiry;
    }
    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
