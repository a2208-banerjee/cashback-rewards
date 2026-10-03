package com.rewardsdojo.cashback.rewards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CardRequest(
        @NotBlank String cardholderName,
        @NotBlank @Pattern(regexp = "\\d{4}", message = "must be exactly 4 digits") String lastFourDigits
) {}
