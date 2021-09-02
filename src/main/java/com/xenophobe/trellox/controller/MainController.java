package com.xenophobe.trellox.controller;

import com.xenophobe.trellox.dto.BoardOutputDto;
import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.router.Router;
import com.xenophobe.trellox.utils.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;


@RestController
@Validated
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private final Router router;


    public MainController(Router router){
        this.router=router;
    }


    @PostMapping(value = "/createUser")
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody User user){
        //call the router
        LOG.debug("CreateUser request {}",user);
         UserOutputDto userOutputDto= router.createUser(user);
        LOG.debug("CreateUser response {}",userOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(userOutputDto);
    }

//    @PostMapping(path = "/sendEmailVerification")
//    public ResponseEntity<UserOutputDto> sendEmailVerification(
//            @NotEmpty   @Email  String email )
//    {
//        LOG.debug("emailVerification request {}",email);
//        UserOutputDto userOutputDto= router.sendEmailVerification(email);
//        LOG.debug("emailVerification response {}",userOutputDto);
//
//        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
//        return bodyBuilder.body(userOutputDto);
//    }
    @PostMapping(path = "/verifyEmail")
    public ResponseEntity<UserOutputDto> verifyEmail(
            @NotEmpty   @Email String email,
            @NotEmpty String providedToken)
    {
        LOG.debug("verifyEmail request {} {}   ", email,providedToken);
        UserOutputDto userOutputDto= router.verifyEmail(email,providedToken);
        LOG.debug("verifyEmail response {}",userOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(userOutputDto);

    }


    @PostMapping(value = "/login")
    public ResponseEntity<UserOutputDto> loginUser(
            @NotEmpty @Email  String email,
            @NotEmpty  String password)
    {

        LOG.debug("loginUser request {} {}",email,password);
        UserOutputDto userOutputDto= router.loginUser(email,password);
        LOG.debug("loginUser response {}",userOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(userOutputDto);

    }

    @PostMapping("/createBoard")
    public ResponseEntity<BoardOutputDto> createBoard(
             @NotEmpty @Size(min = 5) String boardName,
             @NotNull boolean isVisible,
             @NotEmpty  String userToken){

        LOG.debug("CreateBoard request {} {} {}", boardName,isVisible,userToken);
        BoardOutputDto boardOutputDto= router.createBoard( boardName, isVisible,userToken);
        LOG.debug("CreateBoard response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }

    @PostMapping(path = "/addMember/{boardName}")
    public ResponseEntity<BoardOutputDto> addBoardMember(
            @NotEmpty @Email String potentialMemberEmail,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken)
    {
        LOG.debug("addMember request {} {} {}", boardName,potentialMemberEmail,userToken);
        BoardOutputDto boardOutputDto= router.addBoardMember(potentialMemberEmail,boardName,userToken);
        LOG.debug("addMember response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }


    @PutMapping(path = "/addList/{boardName}")
    public ResponseEntity<BoardOutputDto> addList(
          @NotEmpty String listName,
          @PathVariable(required = true,name = "boardName") String boardName,
          @NotEmpty String userToken)
    {
        LOG.debug("addList request {} {} {}", boardName,listName,userToken);
        BoardOutputDto boardOutputDto= router.addList(listName,boardName,userToken);
        LOG.debug("addList response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }

    @PostMapping(path = "/addCard/{boardName}")
    public ResponseEntity<BoardOutputDto> addCard(
            @NotEmpty String cardName,
            @NotNull Integer listId,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken)
    {

        LOG.debug("addCard request {} {} {}", boardName,cardName,listId);
        BoardOutputDto boardOutputDto= router.addCard(cardName,listId,boardName,userToken);
        LOG.debug("AddCard response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }

    @PutMapping(path = "/updateCard/{boardName}")
    public ResponseEntity<BoardOutputDto> updateCard(

              @NotNull Integer cardId,
            String cardDescription,
             Instant dueDate,
             ListWrapper<String> comments,
             ArrayList<String> membersEmails,
            @PathVariable(name = "boardName") String boardName,
             @NotEmpty String userToken

    ){
        LOG.debug("UpdateCard request {} {} {} {} {} {} ", cardId,cardDescription,dueDate,comments,membersEmails,boardName);
        BoardOutputDto boardOutputDto= router.updateCard(cardId,cardDescription,dueDate,comments,membersEmails,boardName,userToken);
        LOG.debug("UpdateCard response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }


    @DeleteMapping(path = "/deleteList/{boardName}")
    public ResponseEntity<BoardOutputDto> deleteList(

            @NotNull Integer listId,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken

    ){
        LOG.debug("DeleteList request {} {}  ", listId,boardName);
        BoardOutputDto boardOutputDto= router.deleteList(listId,boardName,userToken);
        LOG.debug("DeleteList response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }

    @DeleteMapping(path = "/deleteCard/{boardName}")
    public ResponseEntity<BoardOutputDto> deleteCard(

            @NotNull Integer cardId,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken )
    {
        LOG.debug("DeleteList request {} {}  ", cardId,boardName);
        BoardOutputDto boardOutputDto= router.deleteCard(cardId,boardName,userToken);
        LOG.debug("DeleteList response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);
    }

    @PutMapping(path = "/changeVisibility/{boardName}")
    public ResponseEntity<BoardOutputDto> changeBoardVisibility(
             boolean isVisible,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken )
    {
        LOG.debug("ChangeVisibility request {} {}  ", isVisible,boardName);
        BoardOutputDto boardOutputDto= router.changeBoardVisibility(isVisible,boardName,userToken);
        LOG.debug("ChangeVisibility response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);

    }

    @PostMapping(path = "/moveCard/{boardName}")
    public ResponseEntity<BoardOutputDto> moveCard(
            int cardId,
            int newListId,
            @PathVariable(name = "boardName") String boardName,
            @NotEmpty String userToken )

    {
        LOG.debug("moveCard request {} {} {}  ", cardId,newListId,boardName);
        BoardOutputDto boardOutputDto= router.moveCard(cardId,newListId,boardName,userToken);
        LOG.debug("moveCard response {}",boardOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(boardOutputDto);

    }








}
