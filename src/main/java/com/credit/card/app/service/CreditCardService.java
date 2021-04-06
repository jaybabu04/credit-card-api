package com.credit.card.app.service;

import com.credit.card.app.domain.CardInfoEntity;
import com.credit.card.app.exceptions.BusinessException;
import com.credit.card.app.model.CardInfo;
import com.credit.card.app.repository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditCardService {
    private CreditCardRepository creditCardRepository;
    private ModelMapper mapper;
    private MessageSource messageSource;

    public CardInfo save(CardInfo cardInfo){
        //Check cardnumber already exist or not
        if(findByCardNumber(cardInfo.getCardNumber())!=null) {
            throw new BusinessException(messageSource.getMessage("card.number.in.user",null, Locale.ENGLISH));
        }
        CardInfoEntity cardInfoEntity = mapper.map(cardInfo, CardInfoEntity.class);
        creditCardRepository.save(cardInfoEntity);
        return mapper.map(cardInfoEntity, CardInfo.class);
    }

    public CardInfo findByCardNumber(String cardNumber){
        CardInfoEntity cardInfo= creditCardRepository.findByCardNumber(cardNumber);
        return cardInfo!=null? mapper.map(cardInfo, CardInfo.class) : null;

    }
    public List<CardInfo> getAllCardInfo(){
        return creditCardRepository.findAll().stream().map(entity->(mapper.map(entity, CardInfo.class))).collect(Collectors.toList());
    }
}
