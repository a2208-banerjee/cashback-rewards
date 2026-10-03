package com.rewardsdojo.cashback.rewards.controller;

import com.rewardsdojo.cashback.rewards.dto.CardRequest;
import com.rewardsdojo.cashback.rewards.dto.CardResponse;
import com.rewardsdojo.cashback.rewards.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@Valid @RequestBody CardRequest request) {
        return cardService.createCard(request);
    }

    @GetMapping("/{id}")
    public CardResponse getCard(@PathVariable Long id) {
        return cardService.getCard(id);
    }
}
