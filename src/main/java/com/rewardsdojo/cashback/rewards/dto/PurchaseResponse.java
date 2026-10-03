package com.rewardsdojo.cashback.rewards.dto;

import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(
        Long id,
        Long cardId,
        BigDecimal amount,
        PurchaseCategory category,
        String merchant,
        BigDecimal cashbackEarned,
        LocalDateTime purchasedAt
) {}
