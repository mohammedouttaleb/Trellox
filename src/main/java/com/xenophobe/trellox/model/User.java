package com.xenophobe.trellox.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "USER_NAME")
    private  String userName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "BIO")
    private String bio;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Board> boards;

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
}
