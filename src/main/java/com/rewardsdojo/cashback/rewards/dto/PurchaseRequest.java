package com.rewardsdojo.cashback.rewards.dto;

import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PurchaseRequest(
        @NotNull Long cardId,
        @NotNull @Positive BigDecimal amount,
        @NotNull PurchaseCategory category,
        @NotBlank String merchant
) {}
