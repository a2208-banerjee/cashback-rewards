package com.rewardsdojo.cashback.rewards.controller;

import com.rewardsdojo.cashback.rewards.dto.CardRequest;
import com.rewardsdojo.cashback.rewards.dto.CardResponse;
import com.rewardsdojo.cashback.rewards.exception.CardNotFoundException;
import com.rewardsdojo.cashback.rewards.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CardService cardService;

    @Test
    void createCard_validRequest_returns201WithBody() throws Exception {
        CardRequest req = new CardRequest("Jane Doe", "4242");
        CardResponse resp = new CardResponse(1L, "Jane Doe", "4242", LocalDateTime.now());

        when(cardService.createCard(any())).thenReturn(resp);

        mvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardholderName").value("Jane Doe"))
                .andExpect(jsonPath("$.lastFourDigits").value("4242"));
    }

    @Test
    void createCard_blankName_returns400() throws Exception {
        CardRequest req = new CardRequest("", "4242");

        mvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCard_invalidLastFourDigits_returns400() throws Exception {
        CardRequest req = new CardRequest("Jane Doe", "ABCD");

        mvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCard_exists_returns200() throws Exception {
        CardResponse resp = new CardResponse(5L, "Bob Smith", "9999", LocalDateTime.now());
        when(cardService.getCard(5L)).thenReturn(resp);

        mvc.perform(get("/api/cards/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardholderName").value("Bob Smith"));
    }

    @Test
    void getCard_notFound_returns404() throws Exception {
        when(cardService.getCard(99L)).thenThrow(new CardNotFoundException(99L));

        mvc.perform(get("/api/cards/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
