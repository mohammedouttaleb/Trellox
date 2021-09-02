package com.xenophobe.trellox.service;


import com.xenophobe.trellox.exception.CardNotFoundException;
import com.xenophobe.trellox.model.Card;
import com.xenophobe.trellox.repository.CardRepository;
import org.springframework.stereotype.Service;


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

    Card isCardValid(int cardId, String errorMessage) {
        Optional<Card> optionalCard=  cardRepository.findById(cardId);
        if(optionalCard.isEmpty()) throw  new CardNotFoundException("CardNotFoundException",errorMessage);
        return  optionalCard.get();
    }

    Card findCardById(int cardId){
        //i called isListValid just because it feeds my logic needs but not because i want to verify the validity of the list
        return isCardValid(cardId,"Card NotFound");
    }


    public void deleteCardById(Integer cardId) {
        cardRepository.deleteById(cardId);
    }
}
