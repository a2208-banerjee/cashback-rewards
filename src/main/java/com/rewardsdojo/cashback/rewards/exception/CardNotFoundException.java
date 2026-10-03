package com.rewardsdojo.cashback.rewards.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long cardId) {
        super("Card not found: " + cardId);
    }
}
