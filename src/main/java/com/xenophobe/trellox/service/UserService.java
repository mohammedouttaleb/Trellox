package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public UserOutputDto createUser(User user){
        userRepository.save(user);
        return  null;
    }
}
