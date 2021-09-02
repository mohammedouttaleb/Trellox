package com.xenophobe.trellox.dto;



import java.time.Instant;
import java.util.List;

public record CardOutputDto(

        int cardId,
        String cardName,
        String cardDescription,
        Instant dueDate,
        List<String> comments,
        List<String> membersEmails

) {
}
