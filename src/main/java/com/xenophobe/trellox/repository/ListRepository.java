package com.xenophobe.trellox.repository;

import com.xenophobe.trellox.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List,Integer> {
}
