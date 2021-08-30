package com.xenophobe.trellox.exception;

public class BoardNotFoundException extends BoardException {

    public BoardNotFoundException(String boardNotFoundException, String no_board_has_been_found) {
        super(boardNotFoundException,no_board_has_been_found);
    }
}
