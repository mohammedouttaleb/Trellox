package com.xenophobe.trellox.utils;

import java.util.ArrayList;

//this class is created to solve the list problem in the controller,but it couldn't make it yet
public class ListWrapper<T> {


    private ArrayList<T> listOfTType;


    public ArrayList<T> getListOfTType() {
        return listOfTType;
    }

    public void setListOfTType(ArrayList<T> listOfTType) {
        this.listOfTType = listOfTType;
    }

    @Override
    public String toString() {
        return "ListWrapper{" +
                "listOfTType=" + listOfTType +
                '}';
    }
}
