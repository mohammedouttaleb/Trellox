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
import java.util.HashMap;
import java.util.Map;
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


     Board boardExists(String boardName,String errorMessage){
        Optional<Board> optionalBoard= boardRepository.findByBoardName(boardName);
        if(optionalBoard.isEmpty())  throw new BoardNotFoundException("BoardNotFoundException",errorMessage);
        return  optionalBoard.get();
    }


    public BoardOutputDto createBoard(String boardName,boolean isVisible,String userToken){

        //check if the user is valid
        User user= userService.isUserValid(userToken,"You are not allowed to create a board.Please register and try again ");

        Board board=new Board(boardName,isVisible,user);
         boardRepository.save(board);

        return ModelToDtoMapper.mapToBoard(board);
    }

    public BoardOutputDto addBoardMember(
            String potentialMemberEmail,
            String boardName,
            String userToken)
    {
        //check if the board's  owner is valid
        userService.isUserValid(userToken,"You are not allowed to add a member to the  board.Please register and try again ");


        //check that the potential new member has already an account in trellox otherwise he can't be added
        User memberUser= userService.isUserValidByEmail(potentialMemberEmail,"You try to add a non registered  user to the board!!");

         //check is the board exists
         Board board= boardExists(boardName,"No board has been found");

         //if all the tests passed than i add him as a member in the board
          board.addMember(memberUser.getEmail());
          boardRepository.save(board);

          return ModelToDtoMapper.mapToBoard(board);
    }

    public BoardOutputDto addlist(String listName, String boardName, String userToken) {

        //check if the user is valid
        userService.isUserValid(userToken,"You are not allowed to add a List.Please register and try again");


        //check is the board exists
        Board myBoard= boardExists(boardName,"No board has been found");

        //if it's Okk i will create and add the list to the board
        List myList=new List(listName,myBoard,null);
        listService.saveList(myList);
        //add the list to the board for display purpose we don't need to save it
        myBoard.addList(myList);

        return ModelToDtoMapper.mapToBoard(myBoard);

    }
    public BoardOutputDto addCard(String cardName, Integer listId, String boardName, String userToken) {

        //check if the user is valid
        userService.isUserValid(userToken,"You are not allowed to add a card.Please register and try again");

        //check if the list is valid
        List myList=listService.isListValid(listId,"you try to add a card to a nonexistent List");

        //if it's Okk i will create and add the card to the list
        Card myCard=new Card(cardName,myList);
        cardService.save(myCard);


        myList.addCard(myCard);
        listService.saveList(myList);

        //retrieve board data
        Board board= boardExists(boardName,"No board has been found");

        return  ModelToDtoMapper.mapToBoard(board);
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
        userService.isUserValid(userToken,"You are not allowed to update a card.Please register and try again");

        //check if the card is valid also
        Card ourCard=cardService.isCardValid(cardId,"No Card was found with that Id");


        //if it's good we will check which params are not null and we will update our card
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
        Board board= boardExists(boardName,"No board has been found");

        return  ModelToDtoMapper.mapToBoard(board);
    }



    public BoardOutputDto deleteList(Integer listId, String boardName, String userToken) {

        //i should check if the user is valid and if he is a owner of the board otherwise he can't make this operation

        //check the validity of the user
        User user=userService.isUserValid(userToken,"You are not allowed to delete a List.Please register and try again");
        //check if the board exists and if he is the owner of that board
        Board board= boardExists(boardName,"No board has been found");
        if(! board.getOwner().getEmail().equals(user.getEmail())) throw  new InvalidCredentialsException("AuthorizationException","you are not allowed to modify this board");

        //retrieve list data
        List myList= listService.findListById(listId);
        //if it's all Okk we will delete the list
        listService.deleteListById(listId);
         board.removeList(myList);


         return  ModelToDtoMapper.mapToBoard(board);
    }

    public BoardOutputDto deleteCard(Integer cardId, String boardName, String userToken) {

        //i should check if the user is valid and if he is a owner of the board otherwise he can't make this operation

        //check the validity of the user
        User user=userService.isUserValid(userToken,"You are not allowed to delete a Card.Please register and try again");
        //check if the board exists and if he is the owner of that board
        Board board= boardExists(boardName,"No board has been found");
        if(! board.getOwner().getEmail().equals(user.getEmail())) throw  new InvalidCredentialsException("AuthorizationException","you are not allowed to modify this board");

        //retrieve card data
        Card myCard= cardService.findCardById(cardId);
        //if it's all Okk we will delete the card
        cardService.deleteCardById(cardId);

        //retrieve board data
         board= boardExists(boardName,"No board has been found");
         //i don't need the listId so i can get the list object and then remove the card from it .
        // but i iterate over the list of list and i remove the card from all of them
         board.getLists().forEach( list ->  list.removeCard(myCard));


         return  ModelToDtoMapper.mapToBoard(board);
    }
}
