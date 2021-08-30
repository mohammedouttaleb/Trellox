package com.xenophobe.trellox.mapper;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.dto.CardOutputDto;
import com.xenophobe.trellox.dto.ListOutputDto;
import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.Board;
import com.xenophobe.trellox.model.Card;
import com.xenophobe.trellox.model.User;

import java.util.ArrayList;
import java.util.List;

public class ModelToDtoMapper {




    public static BoardOutputDto mapToBoard(Board board){
        if(board==null) return null;
        List<ListOutputDto> listOutputDtos=  mapAllToList(board.getLists());
        BoardOutputDto boardOutputDto=new BoardOutputDto(board.getBoardName(), board.isVisible(), board.getMembers(),listOutputDtos);
        return boardOutputDto;
    }
    private static List<ListOutputDto> mapAllToList(List<com.xenophobe.trellox.model.List> lists){
        if( lists==null) return null;
         List<ListOutputDto> listOutputDtos=new ArrayList<>();

        for (com.xenophobe.trellox.model.List list: lists) {
            List<CardOutputDto> cardOutputDtos=mapAllToCard(list.getCardList());
            listOutputDtos.add( new ListOutputDto(list.getListId(), list.getListName(), cardOutputDtos) );
        }
        return  listOutputDtos;
    }

    private static List<CardOutputDto> mapAllToCard(List<Card> cardList){
        if( cardList==null) return null;
        List<CardOutputDto> cardOutputDtos=new ArrayList<>();
        for (Card card: cardList) {
            List<UserOutputDto> userOutputDtos=mapAllToUser(card.getMembers());
            cardOutputDtos.add( new CardOutputDto(card.getCardId(),card.getCardName(), card.getCardDescription(),card.getDueDate(),card.getComments(),userOutputDtos) );
        }
        return  cardOutputDtos;
    }

    private static List<UserOutputDto> mapAllToUser(List<User> userList){
        if( userList==null) return null;
        List<UserOutputDto> userOutputDtos=new ArrayList<>();
        for (User user: userList) {
            userOutputDtos.add( new UserOutputDto(user.getEmail()));
        }
        return  userOutputDtos;
    }


}
