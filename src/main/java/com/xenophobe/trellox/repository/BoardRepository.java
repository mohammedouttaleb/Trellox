package com.xenophobe.trellox.repository;

import com.xenophobe.trellox.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
}
