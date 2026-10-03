package com.rewardsdojo.cashback.rewards.controller;

import com.rewardsdojo.cashback.rewards.dto.PurchaseRequest;
import com.rewardsdojo.cashback.rewards.dto.PurchaseResponse;
import com.rewardsdojo.cashback.rewards.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse recordPurchase(@Valid @RequestBody PurchaseRequest request) {
        return purchaseService.recordPurchase(request);
    }

    @GetMapping
    public List<PurchaseResponse> getPurchases(@RequestParam Long cardId) {
        return purchaseService.getPurchasesForCard(cardId);
    }
}
