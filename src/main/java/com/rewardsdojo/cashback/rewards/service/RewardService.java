package com.rewardsdojo.cashback.rewards.service;

import com.rewardsdojo.cashback.rewards.dto.RewardSummaryResponse;
import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;
import com.rewardsdojo.cashback.rewards.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

@Service
public class RewardService {

    static final Map<PurchaseCategory, BigDecimal> CASHBACK_RATES = new EnumMap<>(PurchaseCategory.class);

    static {
        CASHBACK_RATES.put(PurchaseCategory.GROCERIES,     new BigDecimal("0.03"));
        CASHBACK_RATES.put(PurchaseCategory.DINING,        new BigDecimal("0.04"));
        CASHBACK_RATES.put(PurchaseCategory.GAS,           new BigDecimal("0.02"));
        CASHBACK_RATES.put(PurchaseCategory.TRAVEL,        new BigDecimal("0.05"));
        CASHBACK_RATES.put(PurchaseCategory.ENTERTAINMENT, new BigDecimal("0.02"));
        CASHBACK_RATES.put(PurchaseCategory.OTHER,         new BigDecimal("0.01"));
    }

    private final PurchaseRepository purchaseRepository;

    public RewardService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public BigDecimal calculateCashback(BigDecimal amount, PurchaseCategory category) {
        BigDecimal rate = CASHBACK_RATES.getOrDefault(category, CASHBACK_RATES.get(PurchaseCategory.OTHER));
        return amount.multiply(rate).setScale(4, RoundingMode.HALF_UP);
    }

    public RewardSummaryResponse getSummary(Long cardId) {
        var purchases = purchaseRepository.findByCardId(cardId);

        Map<PurchaseCategory, BigDecimal> byCategory = new EnumMap<>(PurchaseCategory.class);
        BigDecimal total = BigDecimal.ZERO;

        for (var purchase : purchases) {
            byCategory.merge(purchase.getCategory(), purchase.getCashbackEarned(), BigDecimal::add);
            total = total.add(purchase.getCashbackEarned());
        }

        return new RewardSummaryResponse(cardId, total.setScale(4, RoundingMode.HALF_UP), byCategory);
    }

    public Map<PurchaseCategory, BigDecimal> getRates() {
        return Map.copyOf(CASHBACK_RATES);
    }
}
