package com.rewardsdojo.cashback.rewards.controller;

import com.rewardsdojo.cashback.rewards.dto.PurchaseRequest;
import com.rewardsdojo.cashback.rewards.dto.PurchaseResponse;
import com.rewardsdojo.cashback.rewards.exception.CardNotFoundException;
import com.rewardsdojo.cashback.rewards.model.PurchaseCategory;
import com.rewardsdojo.cashback.rewards.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PurchaseService purchaseService;

    @Test
    void recordPurchase_validRequest_returns201WithCashback() throws Exception {
        PurchaseRequest req = new PurchaseRequest(1L, new BigDecimal("75.00"), PurchaseCategory.DINING, "Sushi Palace");
        PurchaseResponse resp = new PurchaseResponse(1L, 1L, new BigDecimal("75.00"), PurchaseCategory.DINING,
                "Sushi Palace", new BigDecimal("3.0000"), LocalDateTime.now());

        when(purchaseService.recordPurchase(any())).thenReturn(resp);

        mvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cashbackEarned").value(3.0))
                .andExpect(jsonPath("$.category").value("DINING"))
                .andExpect(jsonPath("$.merchant").value("Sushi Palace"));
    }

    @Test
    void recordPurchase_negativeAmount_returns400() throws Exception {
        PurchaseRequest req = new PurchaseRequest(1L, new BigDecimal("-10.00"), PurchaseCategory.DINING, "Test");

        mvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void recordPurchase_missingMerchant_returns400() throws Exception {
        PurchaseRequest req = new PurchaseRequest(1L, new BigDecimal("50.00"), PurchaseCategory.GROCERIES, "");

        mvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void recordPurchase_cardNotFound_returns404() throws Exception {
        PurchaseRequest req = new PurchaseRequest(99L, new BigDecimal("50.00"), PurchaseCategory.GAS, "Shell");

        when(purchaseService.recordPurchase(any())).thenThrow(new CardNotFoundException(99L));

        mvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPurchases_returnsListForCard() throws Exception {
        PurchaseResponse resp = new PurchaseResponse(1L, 2L, new BigDecimal("100.00"), PurchaseCategory.GROCERIES,
                "Costco", new BigDecimal("3.0000"), LocalDateTime.now());

        when(purchaseService.getPurchasesForCard(2L)).thenReturn(List.of(resp));

        mvc.perform(get("/api/purchases").param("cardId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].merchant").value("Costco"))
                .andExpect(jsonPath("$[0].category").value("GROCERIES"));
    }
}
