package com.xenophobe.trellox.dto;



public record BoardOutputDto(

        String boardName,
        boolean isVisible,
        java.util.List<String> membersEmail,
        java.util.List<ListOutputDto> lists
)
{}
