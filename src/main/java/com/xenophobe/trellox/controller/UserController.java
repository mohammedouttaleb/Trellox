package com.xenophobe.trellox.controller;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.router.Router;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private final Router router;

    public UserController(Router router){
        this.router=router;
    }


    @PostMapping("/createUser")
    public ResponseEntity<UserOutputDto> createUser(User user){

        //call the router
         UserOutputDto userOutputDto= router.createUser(user);

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.status(200);
        return bodyBuilder.body(userOutputDto);
    }
}
