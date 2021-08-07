package com.xenophobe.trellox.model;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
