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
import java.util.List;

@RestController
@Validated
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private final Router router;

    public MainController(Router router){
        this.router=router;
    }


    @PostMapping(value = "/createUser")
    /*I should add an email verification only user with  attribute(i should add it to the model) active=true  can
    * work with trellox using that process i will not need to send invitation links
    * to people**/
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody User user){
        //call the router
        LOG.debug("CreateUser request {}",user);
         UserOutputDto userOutputDto= router.createUser(user);
        LOG.debug("CreateUser response {}",userOutputDto);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(userOutputDto);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<UserOutputDto> loginUser(
            @NotEmpty @Email @RequestBody String email,
            @NotEmpty @RequestBody String password)
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
            @PathVariable(required = true,name = "boardName") String boardName,
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

}
