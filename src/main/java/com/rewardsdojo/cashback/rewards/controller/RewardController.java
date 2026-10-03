package com.rewardsdojo.cashback.rewards.controller;

import com.rewardsdojo.cashback.rewards.dto.RewardSummaryResponse;
import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;
import com.rewardsdojo.cashback.rewards.service.RewardService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{cardId}")
    public RewardSummaryResponse getSummary(@PathVariable Long cardId) {
        return rewardService.getSummary(cardId);
    }

    @GetMapping("/rates")
    public Map<PurchaseCategory, BigDecimal> getRates() {
        return rewardService.getRates();
    }
}
