package com.rewardsdojo.cashback.rewards.dto;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        String cardholderName,
        String lastFourDigits,
        LocalDateTime createdAt
) {}
