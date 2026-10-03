package com.rewardsdojo.cashback.rewards.repository;

import com.rewardsdojo.cashback.rewards.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {}
