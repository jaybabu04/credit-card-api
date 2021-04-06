package com.credit.card.app.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.credit.card.app.domain.CardInfoEntity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class CreditCardRepositoryTest {
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    public void testFindByCardNumber() {
        CardInfoEntity cardInfoEntity = new CardInfoEntity();
        cardInfoEntity.setUpdatedDateTime(LocalDateTime.now());
        cardInfoEntity.setCreatedDateTime(LocalDateTime.now());
        cardInfoEntity.setId(1L);
        cardInfoEntity.setCreditlimit(10000.0);
        cardInfoEntity.setCreatedBy("System");
        cardInfoEntity.setName("Ganesh");
        cardInfoEntity.setActive(false);
        cardInfoEntity.setUpdatedBy("");
        cardInfoEntity.setCardNumber("79927398713");
        cardInfoEntity.setBalance(0.0);
        this.creditCardRepository.save(cardInfoEntity);
        assertNotNull(this.creditCardRepository.findByCardNumber("79927398713"));
    }
}

