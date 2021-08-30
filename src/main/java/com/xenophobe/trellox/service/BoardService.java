package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.exception.*;
import com.xenophobe.trellox.mapper.ModelToDtoMapper;
import com.xenophobe.trellox.model.Board;
import com.xenophobe.trellox.model.Card;
import com.xenophobe.trellox.model.List;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.repository.BoardRepository;
import com.xenophobe.trellox.utils.ListWrapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ListService listService;
    private final CardService cardService;


    public BoardService(BoardRepository boardRepository,
                        UserService userService,
                        ListService listService,
                        CardService cardService )
    {
        this.boardRepository = boardRepository;
        this.userService=userService;
        this.listService=listService;
        this.cardService=cardService;
    }


     Optional<Board> boardExists(String boardName){
        return boardRepository.findByBoardName(boardName);
    }


    public BoardOutputDto createBoard(String boardName,boolean isVisible,String userToken){

        //check if the user is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
         if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to create a board.Please register and try again");
         Board board=new Board(boardName,isVisible,userOptional.get());
         boardRepository.save(board);
        return ModelToDtoMapper.mapToBoard(board);
    }
    public BoardOutputDto addBoardMember(
            String potentialMemberEmail,
            String boardName,
            String userToken)
    {
        //check if the board's  owner is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
        if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to create a board.Please register and try again");

        //check that the potential new member has already an account in trellox otherwise he can't be added
        userOptional= userService.findUserByEmail(potentialMemberEmail);
        if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You try to add a non registered  user to the board!!");

         //check is the board exists
         Optional<Board> potentialBoard= boardExists(boardName);
         if(potentialBoard.isEmpty()) throw new BoardNotFoundException("BoardNotFoundException","No board has been found");

         //if all the tests passed than i add him as a member in the board
          Board board=  potentialBoard.get();
          board.addMember(userOptional.get().getEmail());
          boardRepository.save(board);

          return ModelToDtoMapper.mapToBoard(board);
    }

    public BoardOutputDto addlist(String listName, String boardName, String userToken) {

        //check if the user is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
        if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to create a board.Please register and try again");

        //check is the board exists
        Optional<Board> potentialBoard= boardExists(boardName);
        if(potentialBoard.isEmpty()) throw new BoardNotFoundException("BoardNotFoundException","No board has been found");

        //if it's Okk i will create and add the list to the board
        Board myBoard=potentialBoard.get();
        List myList=new List(listName,myBoard,null);
        listService.saveList(myList);
        //add the list to the board for display purpose we don't need to save it
        myBoard.addList(myList);

        return ModelToDtoMapper.mapToBoard(myBoard);

    }
    public BoardOutputDto addCard(String cardName, Integer listId, String boardName, String userToken) {

        //check if the user is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
        if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to create a board.Please register and try again");

        //check if the list is valid
        Optional<List> listOptional=listService.findListById(listId);
        if(listOptional.isEmpty()) throw  new ListNotFoundException("ListNotFoundException","you try to add a card to a nonexistent List");

        //if it's Okk i will create and add the card to the list
        List myList=listOptional.get();
        Card myCard=new Card(cardName,myList);
        cardService.save(myCard);


        myList.addCard(myCard);
        listService.saveList(myList);

        //retrieve board data
        Optional<Board> potentialBoard= boardExists(boardName);
        if(potentialBoard.isEmpty()) throw new BoardNotFoundException("BoardNotFoundException","No board has been found");




        return  ModelToDtoMapper.mapToBoard(potentialBoard.get());
    }

    public BoardOutputDto updateCard(
            int cardId,
            String cardDescription,
            Instant dueDate,
            ListWrapper<String> comments,
            java.util.List<String> membersEmails,
            String boardName,
            String userToken )
    {
        //check if the user is valid
        Optional<User> userOptional=userService.isUserValid(userToken);
        if(userOptional.isEmpty()) throw  new UserNotFoundException("UserNotFoundException","You are not allowed to update a card.Please register and try again");

        //check if the card is valid also
        Optional<Card> cardOptional=cardService.findCardById(cardId);
        if(cardOptional.isEmpty()) throw  new CardNotFoundException("CardNotFoundException","No Card was found with that Id");

        //if it's good we will check which params are not null and we will update our card
        Card ourCard=cardOptional.get();
        if(cardDescription!=null) ourCard.setCardDescription( cardDescription);
        if(dueDate!=null) ourCard.setDueDate(dueDate);
        if(comments!=null && comments.getListOfTType()!=null && !comments.getListOfTType().isEmpty()) ourCard.setComments(comments.getListOfTType());

        //members logic
        java.util.List<User> userList=new ArrayList<>();
        if(membersEmails!=null && !membersEmails.isEmpty()){

           membersEmails.forEach(
                   email -> {
                       Optional<User> optionalUser=userService.findUserByEmail(email);
                       optionalUser.ifPresent(userList::add);
                   }
           );
            ourCard.setMembers(userList);
        }

        cardService.save(ourCard);



        //retrieve board data
        Optional<Board> potentialBoard= boardExists(boardName);
        if(potentialBoard.isEmpty()) throw new BoardNotFoundException("BoardNotFoundException","No board has been found");

        return  ModelToDtoMapper.mapToBoard(potentialBoard.get());
    }
}
