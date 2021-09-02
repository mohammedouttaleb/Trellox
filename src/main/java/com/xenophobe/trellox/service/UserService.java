package com.xenophobe.trellox.service;

import com.xenophobe.trellox.dto.UserOutputDto;
import com.xenophobe.trellox.exception.EmailAlreadyExistsException;
import com.xenophobe.trellox.exception.InvalidCredentialsException;
import com.xenophobe.trellox.exception.UserNotFoundException;
import com.xenophobe.trellox.model.User;
import com.xenophobe.trellox.repository.UserRepository;
import com.xenophobe.trellox.utils.AES;
import com.xenophobe.trellox.utils.ObjectUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AES encryptionObject;
    private final JavaMailSender javaMailSender;


    public UserService(UserRepository userRepository,AES encryptionObject,JavaMailSender javaMailSender)
    {
        this.userRepository=userRepository;
        this.encryptionObject=encryptionObject;
        this.javaMailSender=javaMailSender;

    }


    public UserOutputDto createUser( User user){
        if(isEmailValidForSignUp(user.getEmail())) {
            String encryptedPassword=ObjectUtils.safeGet(user.getPassword(), encryptionObject::encrypt);
            user.setPassword(encryptedPassword);
            userRepository.save(user);

            //send Mail verification first to make sure that the mail works if the user doesn't
            //validate his email he can't get a token so he can't send requests
            return sendEmailVerification(user.getEmail());


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
               return new UserOutputDto("login done  with success!!!",token,potentialUser.get().getEmail());
           }


        }
        throw new InvalidCredentialsException("CredentialNotValidException","Invalid Credentials");
    }

     User isUserValid(String userToken,String errorMessage) {

        int userId= Integer.parseInt( encryptionObject.decrypt(userToken));
         Optional<User> optionalUser= userRepository.findById(userId);
         if(optionalUser.isEmpty()) throw  new UserNotFoundException("UserNotFoundException",errorMessage);
         return optionalUser.get();
    }
      Optional<User>  findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    User isUserValidByEmail(String email,String errorMessage) {


        Optional<User> optionalUser= userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) throw  new UserNotFoundException("UserNotFoundException",errorMessage);
        return optionalUser.get();
    }

    //method that send a token to a usermail to verify that his mail is valid
    public UserOutputDto sendEmailVerification(String email) {

        SimpleMailMessage mail=new SimpleMailMessage();
         mail.setTo(email);
         mail.setFrom("oninebankensias@gmail.com");
        mail.setSubject("Trellox Email Verification");
        email= email.replace('@','+');
        String token= encryptionObject.encrypt(email);
        mail.setText("Welcome Trellox Family ;).\n\n\nTo verify your email and become a valid Trellox User, Please use this token : \n\n"+token+" .\n\n\nCordially.\n\n\n\nTrellox Team");
        javaMailSender.send(mail);

        return  new UserOutputDto("mail sent successfully",email);
    }


    public UserOutputDto verifyEmail(String providedToken,String email){

        email=email.replace('@','+');
        String tokenDecrypted=encryptionObject.decrypt(providedToken);
        if(tokenDecrypted==null || !tokenDecrypted.equals(email))
            throw new InvalidCredentialsException("InvalidEmailToken","You failed to verify Your Email.Invalid Token provided");

        else  {
            email=email.replace('+','@');
            User user= findUserByEmail(email).get();
            String token=encryptionObject.encrypt(String.valueOf(user.getUserId()));
            return new UserOutputDto("login done  with success!!!",token,user.getEmail());
        }

    }
}
