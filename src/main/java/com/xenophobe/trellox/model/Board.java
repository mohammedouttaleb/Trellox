package com.xenophobe.trellox.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Table(name = "BOARD")
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARD_ID")
    private int boardId;

    @Column(name = "BOARD_NAME")
    private String boardName;

    @Column(name = "IS_VISIBLE")
    private boolean isVisible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "board")
    private java.util.List<List> lists;


    @Type(type = "json")
    @Column(columnDefinition = "jsonb",name = "MEMBERS")
    private java.util.List<User> members;

    public Board() { }

    public Board(String boardName, boolean isVisible,User owner) {
        this.boardName=boardName;
        this.isVisible=isVisible;
        this.owner=owner;
    }


    public int getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public java.util.List<List> getLists() {
        return lists;
    }

    public void setLists(java.util.List<List> lists) {
        this.lists = lists;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public java.util.List<User> getMembers() {
        return members;
    }


    public void setMembers(java.util.List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", boardName='" + boardName + '\'' +
                ", isVisible=" + isVisible +
                '}';
    }
}
