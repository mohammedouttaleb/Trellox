package com.xenophobe.trellox.repository;

import com.xenophobe.trellox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    @Query("SELECT U.email FROM User U ")
    List<String > getEmails();

    Optional<User>  findByEmail(String email);

    @Query("SELECT U.userId FROM User U Where U.email= :email ")
     int  findUserIdByEmail(@Param("email") String email);

}
