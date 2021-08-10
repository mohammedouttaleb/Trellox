package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.exception.EmailAlreadyExistsException;
import com.xenophobe.trellox.exception.InvalidCredentialsException;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.repository.UserRepository;
import com.xenophobe.trellox.utils.AES;
import com.xenophobe.trellox.utils.ObjectUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AES encryptionObject;


    public UserService(UserRepository userRepository,AES encryptionObject)
    {
        this.userRepository=userRepository;
        this.encryptionObject=encryptionObject;

    }


    public UserOutputDto createUser( User user){
        if(isEmailValidForSignUp(user.getEmail())) {
            String encryptedPassword=ObjectUtils.safeGet(user.getPassword(), encryptionObject::encrypt);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            //generate and send the token  to identify the user during the session
            int userId=userRepository.findUserIdByEmail(user.getEmail());
            String token=encryptionObject.encrypt(String.valueOf(userId));
           return  new UserOutputDto("User saved with success!!!",token);
        }
        throw new EmailAlreadyExistsException("EmailAlreadyExistsException","this email already exists");
    }

    private boolean isEmailValidForSignUp(String email){
        List<String> usersEmail=userRepository.getEmails();
          return  !ObjectUtils.safeGet(usersEmail,strings -> strings.contains(email));
    }

    public UserOutputDto loginUser(String email,String password){
        Optional<User> potentialUser=userRepository.findByEmail(email);
        if(potentialUser.isPresent()){
            String decryptedPassword=encryptionObject.decrypt(potentialUser.get().getPassword());
           if(decryptedPassword.equals(password)){
               String token=encryptionObject.encrypt(String.valueOf(potentialUser.get().getUserId()));
               return new UserOutputDto("login done  with success!!!",token);
           }


        }
        throw new InvalidCredentialsException("CredentialNotValidException","Invalid Credentials");
    }

     Optional<User> isUserValid(String userToken) {

        int userId= Integer.parseInt( encryptionObject.decrypt(userToken));
         return userRepository.findById(userId);
    }
}
