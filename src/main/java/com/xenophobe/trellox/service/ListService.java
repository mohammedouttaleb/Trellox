package com.xenophobe.trellox.service;

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

    Optional<List> findListById(int listId){
        return  listRepository.findById(listId);
    }
}
