package com.xenophobe.trellox.dto;



public record ListOutputDto(

        int listId,
        String listName,
        java.util.List<CardOutputDto> cardList
) {
}
