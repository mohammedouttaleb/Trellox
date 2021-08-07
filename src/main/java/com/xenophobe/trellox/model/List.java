package com.xenophobe.trellox.model;

import javax.persistence.*;

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

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "list")
    private java.util.List<Card> cardList;


    public int getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return "List{" +
                "listId=" + listId +
                ", listName='" + listName + '\'' +
                '}';
    }
}
