package com.xenophobe.trellox.router;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.Board;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.service.BoardService;
import com.xenophobe.trellox.service.CardService;
import com.xenophobe.trellox.service.UserService;
import com.xenophobe.trellox.utils.ListWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.List;

@Component
public class Router {

    private final UserService userService;
    private final BoardService boardService;

    public Router(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }




    public UserOutputDto createUser(User user){

        return userService.createUser(user);
    }

    public UserOutputDto loginUser(String email,String password){

        return  userService.loginUser(email,password);
    }
    public BoardOutputDto createBoard(String boardName,boolean isVisible,String userToken){

        return boardService.createBoard(boardName,isVisible,userToken);
    }

    public BoardOutputDto addBoardMember(
            String potentialMemberEmail,
             String boardName,
            String userToken)
    {
        return boardService.addBoardMember(potentialMemberEmail,boardName,userToken);
    }

    public BoardOutputDto addList(String listName, String boardName, String userToken) {

        return  boardService.addlist(listName,boardName,userToken);
    }

    public BoardOutputDto addCard(String cardName, Integer listId, String boardName, String userToken) {

        return  boardService.addCard(cardName,listId,boardName,userToken);

    }

    public BoardOutputDto updateCard(int cardId, String cardDescription, Instant dueDate, ListWrapper<String> comments, List<String> membersEmails, String boardName, String userToken)
    {
        return  boardService.updateCard(cardId,cardDescription,dueDate,comments,membersEmails,boardName,userToken);
    }

    public BoardOutputDto deleteList(Integer listId, String boardName, String userToken) {

        return  boardService.deleteList(listId,boardName,userToken);
    }

    public BoardOutputDto deleteCard(Integer cardId, String boardName, String userToken) {

        return  boardService.deleteCard(cardId,boardName,userToken);
    }

    public BoardOutputDto changeBoardVisibility(boolean isVisible, String boardName, String userToken) {

        return  boardService.changeBoardVisibility(isVisible,boardName,userToken);
    }

    public BoardOutputDto moveCard(int cardId, int newListId, String boardName, String userToken) {

        return boardService.moveCard(cardId,newListId,boardName,userToken);
    }

//    public UserOutputDto sendEmailVerification(String email) {
//
//        return  userService.sendEmailVerification(email);
//    }

    public UserOutputDto verifyEmail(String email, String providedToken) {

        return  userService.verifyEmail(providedToken,email);
    }
}
