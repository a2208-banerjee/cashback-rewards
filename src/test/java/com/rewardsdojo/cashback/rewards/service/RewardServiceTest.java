package com.rewardsdojo.cashback.rewards.service;

import com.rewardsdojo.cashback.rewards.dto.RewardSummaryResponse;
import com.rewardsdojo.cashback.rewards.model.Card;
import com.rewardsdojo.cashback.rewards.model.Purchase;
import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;
import com.rewardsdojo.cashback.rewards.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    PurchaseRepository purchaseRepository;

    @InjectMocks
    RewardService rewardService;

    @Test
    void calculateCashback_groceries_returns3Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("100.00"), PurchaseCategory.GROCERIES);
        assertThat(cashback).isEqualByComparingTo("3.0000");
    }

    @Test
    void calculateCashback_dining_returns4Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("50.00"), PurchaseCategory.DINING);
        assertThat(cashback).isEqualByComparingTo("2.0000");
    }

    @Test
    void calculateCashback_travel_returns5Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("200.00"), PurchaseCategory.TRAVEL);
        assertThat(cashback).isEqualByComparingTo("10.0000");
    }

    @Test
    void calculateCashback_gas_returns2Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("60.00"), PurchaseCategory.GAS);
        assertThat(cashback).isEqualByComparingTo("1.2000");
    }

    @Test
    void calculateCashback_entertainment_returns2Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("30.00"), PurchaseCategory.ENTERTAINMENT);
        assertThat(cashback).isEqualByComparingTo("0.6000");
    }

    @Test
    void calculateCashback_other_returns1Percent() {
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("100.00"), PurchaseCategory.OTHER);
        assertThat(cashback).isEqualByComparingTo("1.0000");
    }

    @Test
    void calculateCashback_fractionalAmount_roundsHalfUp() {
        // 0.03 * 33.33 = 0.9999 → rounds to 1.0000
        BigDecimal cashback = rewardService.calculateCashback(new BigDecimal("33.33"), PurchaseCategory.GROCERIES);
        assertThat(cashback).isEqualByComparingTo("0.9999");
    }

    @Test
    void getSummary_aggregatesTotalAndByCategory() {
        Card card = new Card("Alice", "1234");
        Purchase p1 = new Purchase(card, new BigDecimal("100.00"), PurchaseCategory.GROCERIES,  "Whole Foods",   new BigDecimal("3.0000"));
        Purchase p2 = new Purchase(card, new BigDecimal("50.00"),  PurchaseCategory.DINING,     "Burger Barn",   new BigDecimal("2.0000"));
        Purchase p3 = new Purchase(card, new BigDecimal("80.00"),  PurchaseCategory.GROCERIES,  "Trader Joe's",  new BigDecimal("2.4000"));

        when(purchaseRepository.findByCardId(1L)).thenReturn(List.of(p1, p2, p3));

        RewardSummaryResponse summary = rewardService.getSummary(1L);

        assertThat(summary.totalCashback()).isEqualByComparingTo("7.4000");
        assertThat(summary.cashbackByCategory().get(PurchaseCategory.GROCERIES)).isEqualByComparingTo("5.4000");
        assertThat(summary.cashbackByCategory().get(PurchaseCategory.DINING)).isEqualByComparingTo("2.0000");
    }

    @Test
    void getSummary_noPurchases_returnsZeroTotal() {
        when(purchaseRepository.findByCardId(99L)).thenReturn(List.of());

        RewardSummaryResponse summary = rewardService.getSummary(99L);

        assertThat(summary.totalCashback()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(summary.cashbackByCategory()).isEmpty();
    }

    @Test
    void getRates_returnsAllSixCategories() {
        assertThat(rewardService.getRates()).hasSize(6);
        assertThat(rewardService.getRates().get(PurchaseCategory.TRAVEL)).isEqualByComparingTo("0.05");
    }
}
