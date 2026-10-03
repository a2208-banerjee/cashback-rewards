package com.rewardsdojo.cashback.rewards.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardholderName;

    @Column(nullable = false, length = 4)
    private String lastFourDigits;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Card() {}

    public Card(String cardholderName, String lastFourDigits) {
        this.cardholderName = cardholderName;
        this.lastFourDigits = lastFourDigits;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getCardholderName() { return cardholderName; }
    public String getLastFourDigits() { return lastFourDigits; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
