package com.credit.card.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.credit.card.app.domain.CardInfoEntity;
import com.credit.card.app.exceptions.BusinessException;
import com.credit.card.app.model.CardInfo;
import com.credit.card.app.repository.CreditCardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class CreditCardServiceTest {

    private CreditCardRepository creditCardRepository;

    private CreditCardService creditCardService;

    @Autowired
    private MessageSource messageSource;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        creditCardRepository = mock(CreditCardRepository.class);
        messageSource = mock(MessageSource.class);
        modelMapper = new ModelMapper();
        creditCardService = new CreditCardService(creditCardRepository, modelMapper, messageSource);
    }

    @Test
    public void should_save_cardinfo() {
         String cardNumber = "79927398713";
         when(this.creditCardRepository.findByCardNumber(cardNumber)).thenReturn(null);
         CardInfo cardInfo =this.creditCardService.save(createCardInfo(cardNumber));
         assertEquals(cardNumber,cardInfo.getCardNumber());
         verify(this.creditCardRepository).findByCardNumber(cardNumber);
     }

    @Test
    public void should_throw_card_already_exit_exception() {
        String cardNumber = "79927398713";
        when(this.creditCardRepository.findByCardNumber("79927398713")).thenReturn(createCardInfoEntity("79927398713"));
        when(messageSource.getMessage("card.number.in.user",null, Locale.ENGLISH)).thenReturn("Card Already Exist");
        assertThrows(BusinessException.class, () -> this.creditCardService.save(createCardInfo("79927398713")));
        verify(this.creditCardRepository).findByCardNumber("79927398713");
    }

    @Test
    public void should_return_cardInfo() {
        String cardNumber ="346680390538702";
        CardInfoEntity cardInfoEntity = createCardInfoEntity(cardNumber);
        when(creditCardRepository.findByCardNumber(cardNumber)).thenReturn(cardInfoEntity);
        CardInfo actualFindByCardNumberResult = creditCardService.findByCardNumber(cardNumber);
        assertEquals(10.0, actualFindByCardNumberResult.getBalance());
        assertFalse(actualFindByCardNumberResult.isActive());
        assertEquals("", actualFindByCardNumberResult.getUpdatedBy());
        assertEquals(cardNumber, actualFindByCardNumberResult.getCardNumber());
        assertEquals("Ganesh Siva", actualFindByCardNumberResult.getName());
        assertEquals("System", actualFindByCardNumberResult.getCreatedBy());
        verify(creditCardRepository).findByCardNumber(cardNumber);
    }

    @Test
    public void should_return_empty_result() {
        when(creditCardRepository.findAll()).thenReturn(new ArrayList<CardInfoEntity>());
        assertTrue(
                creditCardService
                        .getAllCardInfo()
                        .isEmpty());
        verify(creditCardRepository).findAll();
    }

    @Test
    public void should_return_one_result() {
        CardInfoEntity cardInfoEntity =createCardInfoEntity("371680919779991");
        ArrayList<CardInfoEntity> cardInfoEntityList = new ArrayList<CardInfoEntity>();
        cardInfoEntityList.add(cardInfoEntity);
        when(creditCardRepository.findAll()).thenReturn(cardInfoEntityList);
        assertEquals(1,
                creditCardService
                        .getAllCardInfo()
                        .size());
        verify(creditCardRepository).findAll();
    }

    @Test
    public void should_return_two_result() {
        CardInfoEntity cardInfoEntity =createCardInfoEntity("371680919779991");
        CardInfoEntity cardInfoEntity1 =createCardInfoEntity("346680390538702");
        ArrayList<CardInfoEntity> cardInfoEntityList = new ArrayList<CardInfoEntity>();
        cardInfoEntityList.add(cardInfoEntity);
        cardInfoEntityList.add(cardInfoEntity1);
        when(creditCardRepository.findAll()).thenReturn(cardInfoEntityList);
        assertEquals(2,
                creditCardService
                        .getAllCardInfo()
                        .size());
        verify(creditCardRepository).findAll();
    }

    private CardInfoEntity createCardInfoEntity(String cardNumber){
        CardInfoEntity cardInfoEntity = new CardInfoEntity();
        cardInfoEntity.setUpdatedDateTime(LocalDateTime.now());
        cardInfoEntity.setCreatedDateTime(LocalDateTime.now());
        cardInfoEntity.setCreditlimit(1000);
        cardInfoEntity.setCreatedBy("System");
        cardInfoEntity.setName("Ganesh Siva");
        cardInfoEntity.setActive(false);
        cardInfoEntity.setUpdatedBy("");
        cardInfoEntity.setCardNumber(cardNumber);
        cardInfoEntity.setBalance(10.0);
        return  cardInfoEntity;
    }


    private CardInfo createCardInfo(String cardNumber) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setUpdatedDateTime(LocalDateTime.now());
        cardInfo.setCreatedDateTime(LocalDateTime.now());
        cardInfo.setCreditlimit(1000);
        cardInfo.setCreatedBy("System");
        cardInfo.setName("Ganesh Siva");
        cardInfo.setActive(false);
        cardInfo.setUpdatedBy("");
        cardInfo.setCardNumber(cardNumber);
        cardInfo.setBalance(0);
        return  cardInfo;
    }
}
