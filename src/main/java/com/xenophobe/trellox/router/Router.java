package com.xenophobe.trellox.router;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.Board;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.service.BoardService;
import com.xenophobe.trellox.service.UserService;
import org.springframework.stereotype.Component;

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

}
