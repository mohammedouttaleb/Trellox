package com.xenophobe.trellox.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USERS")
/**
 * BY OUBAYDOS
 * ALL RIGHTS RESERVED
 */
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "USER_ID")
    private int userId;

    @NotEmpty
    @Size(min = 5)
    @Column(name = "USER_NAME")
    private  String userName;

    @NotEmpty
    @Email
    @Column(name = "EMAIL",unique = true)
    private String email;

    @NotEmpty
    @Size(min = 6)
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BIO")
    private String bio;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Board> boards;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CARD_USER",joinColumns ={@JoinColumn(name = "USER_ID")},inverseJoinColumns = {@JoinColumn(name = "CARD_ID")})
    private List<Card> cardList;

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", boards=" + boards +
                ", cardList=" + cardList +
                '}';
    }


}
