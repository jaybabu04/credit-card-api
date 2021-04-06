package com.credit.card.app.repository;

import com.credit.card.app.domain.CardInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CardInfoEntity, Long> {

    public CardInfoEntity findByCardNumber(String cardNumber);
}
