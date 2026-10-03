package com.rewardsdojo.cashback.rewards.dto;

import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;

import java.math.BigDecimal;
import java.util.Map;

public record RewardSummaryResponse(
        Long cardId,
        BigDecimal totalCashback,
        Map<PurchaseCategory, BigDecimal> cashbackByCategory
) {}
