package com.xenophobe.trellox.service;

import com.xenophobe.trellox.exception.ListNotFoundException;
import com.xenophobe.trellox.model.List;
import com.xenophobe.trellox.repository.ListRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListService {

    private final ListRepository listRepository;

    public ListService(ListRepository listRepository){
        this.listRepository=listRepository;
    }


    void saveList(List list){
        if(list!=null) listRepository.save(list);
    }

    List isListValid(int listId,String errorMessage){
        Optional<List> optionalList=  listRepository.findById(listId);
        if(optionalList.isEmpty()) throw  new ListNotFoundException("ListNotFoundException",errorMessage);
        return optionalList.get();
    }
     List findListById(int listId){
          //i called isListValid just because it feeds my logic needs but not because i want to verify the validity of the list
        return isListValid(listId,"List NotFound");
    }
     void deleteListById(int listId){
        listRepository.deleteById(listId);
    }
}
