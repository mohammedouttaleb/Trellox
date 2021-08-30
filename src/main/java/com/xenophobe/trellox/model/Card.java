package com.xenophobe.trellox.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "CARD")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CARD_ID")
    private int cardId;

    @Column(name = "CARD_NAME")
    private String cardName;

    @Column(name = "CARD_DESCRIPTION")
    private String cardDescription;

    @Column(name = "DUE_DATE")
    private Instant dueDate;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb",name = "COMMENTS")
    private List<String> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CARD_USER",joinColumns ={@JoinColumn(name = "CARD_ID")},inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIST_ID")
    private com.xenophobe.trellox.model.List list;


    public Card(String cardName, com.xenophobe.trellox.model.List list) {
        this.cardName = cardName;
        this.list = list;
    }
    public Card(){}

    public int getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }


    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", cardName='" + cardName + '\'' +
                ", cardDescription='" + cardDescription + '\'' +
                ", dueDate=" + dueDate +
                ", comments=" + comments +
                ", members=" + members +
                '}';
    }
}
