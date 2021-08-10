package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.exception.UserException;
import com.xenophobe.trellox.exception.UserNotFoundException;
import com.xenophobe.trellox.model.Board;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserService userService;


    public BoardService(BoardRepository boardRepository,
                        UserService userService)
    {
        this.boardRepository = boardRepository;
        this.userService=userService;
    }


    public BoardOutputDto createBoard(String boardName,boolean isVisible,String userToken){

        //check if the user is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
         if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to create a board.Please register and try again");
         Board board=new Board(boardName,isVisible,userOptional.get());
         boardRepository.save(board);
        return new BoardOutputDto("Board Created Successfully");
    }
}
