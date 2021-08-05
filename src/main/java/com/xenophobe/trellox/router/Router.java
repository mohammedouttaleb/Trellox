package com.xenophobe.trellox.router;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class Router {

    private final UserService userService;

    public Router(UserService userService){
        this.userService=userService;
    }


    public UserOutputDto createUser(User user){

        return userService.createUser(user);
    }

}
