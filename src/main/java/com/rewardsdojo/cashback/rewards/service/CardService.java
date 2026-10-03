package com.rewardsdojo.cashback.rewards.service;

import com.rewardsdojo.cashback.rewards.dto.CardRequest;
import com.rewardsdojo.cashback.rewards.dto.CardResponse;
import com.rewardsdojo.cashback.rewards.exception.CardNotFoundException;
import com.rewardsdojo.cashback.rewards.model.Card;
import com.rewardsdojo.cashback.rewards.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardResponse createCard(CardRequest request) {
        Card card = new Card(request.cardholderName(), request.lastFourDigits());
        card = cardRepository.save(card);
        return toResponse(card);
    }

    public CardResponse getCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        return toResponse(card);
    }

    private CardResponse toResponse(Card card) {
        return new CardResponse(card.getId(), card.getCardholderName(), card.getLastFourDigits(), card.getCreatedAt());
    }
}
