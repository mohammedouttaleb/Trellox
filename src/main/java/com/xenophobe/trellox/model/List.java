package com.xenophobe.trellox.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "LIST")
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIST_ID")
    private int listId;

    @Column(name = "LIST_NAME")
    private String listName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    //this cascade params means that if you delete A list all the cards associate to it get deleted also
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "list",cascade = CascadeType.REMOVE)
    private java.util.List<Card> cardList;


    public List() {
    }

    public List(String listName, Board board, java.util.List<Card> cardList) {
        this.listName = listName;
        this.board = board;
        this.cardList = cardList;
    }

    public int getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public java.util.List<Card> getCardList() {
        return cardList;
    }


    public void addCard(Card card){
        if(cardList==null) cardList=new ArrayList<>();
        cardList.add(card);
    }
    public void removeCard(Card card){
        if(cardList==null) cardList=new ArrayList<>();
        cardList.remove(card);
    }


    @Override
    public String toString() {
        return "List{" +
                "listId=" + listId +
                ", listName='" + listName + '\'' +
                '}';
    }
}
