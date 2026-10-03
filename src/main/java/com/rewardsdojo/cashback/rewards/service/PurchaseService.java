package com.rewardsdojo.cashback.rewards.service;

import com.rewardsdojo.cashback.rewards.dto.PurchaseRequest;
import com.rewardsdojo.cashback.rewards.dto.PurchaseResponse;
import com.rewardsdojo.cashback.rewards.exception.CardNotFoundException;
import com.rewardsdojo.cashback.rewards.model.Card;
import com.rewardsdojo.cashback.rewards.model.Purchase;
import com.rewardsdojo.cashback.rewards.repository.CardRepository;
import com.rewardsdojo.cashback.rewards.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseService {

    private final CardRepository cardRepository;
    private final PurchaseRepository purchaseRepository;
    private final RewardService rewardService;

    public PurchaseService(CardRepository cardRepository, PurchaseRepository purchaseRepository, RewardService rewardService) {
        this.cardRepository = cardRepository;
        this.purchaseRepository = purchaseRepository;
        this.rewardService = rewardService;
    }

    public PurchaseResponse recordPurchase(PurchaseRequest request) {
        Card card = cardRepository.findById(request.cardId())
                .orElseThrow(() -> new CardNotFoundException(request.cardId()));

        BigDecimal cashback = rewardService.calculateCashback(request.amount(), request.category());
        Purchase purchase = new Purchase(card, request.amount(), request.category(), request.merchant(), cashback);
        purchase = purchaseRepository.save(purchase);
        return toResponse(purchase);
    }

    public List<PurchaseResponse> getPurchasesForCard(Long cardId) {
        return purchaseRepository.findByCardId(cardId).stream()
                .map(this::toResponse)
                .toList();
    }

    private PurchaseResponse toResponse(Purchase purchase) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getCard().getId(),
                purchase.getAmount(),
                purchase.getCategory(),
                purchase.getMerchant(),
                purchase.getCashbackEarned(),
                purchase.getPurchasedAt()
        );
    }
}
