package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.model.Card;
import com.xenophobe.trellox.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository){
        this.cardRepository=cardRepository;
    }


    void save(Card card){
        if(card!=null)  cardRepository.save(card);
    }

    Optional<Card> findCardById(int cardId) { return  cardRepository.findById(cardId);}



}
