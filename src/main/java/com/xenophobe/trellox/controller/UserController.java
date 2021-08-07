package com.xenophobe.trellox.controller;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final Router router;

    public UserController(Router router){
        this.router=router;
    }


    @PostMapping(value = "/createUser")
    /**after the register action i should generate a token and send it to the client ,he should save
     * it in the local storage and every time he needs to do an action which requires authentication
     * i should verify that he has that token*/
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
}
