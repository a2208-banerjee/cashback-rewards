package com.rewardsdojo.cashback.rewards.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseCategory category;

    @Column(nullable = false)
    private String merchant;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal cashbackEarned;

    @Column(nullable = false)
    private LocalDateTime purchasedAt;

    protected Purchase() {}

    public Purchase(Card card, BigDecimal amount, PurchaseCategory category, String merchant, BigDecimal cashbackEarned) {
        this.card = card;
        this.amount = amount;
        this.category = category;
        this.merchant = merchant;
        this.cashbackEarned = cashbackEarned;
        this.purchasedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Card getCard() { return card; }
    public BigDecimal getAmount() { return amount; }
    public PurchaseCategory getCategory() { return category; }
    public String getMerchant() { return merchant; }
    public BigDecimal getCashbackEarned() { return cashbackEarned; }
    public LocalDateTime getPurchasedAt() { return purchasedAt; }
}
