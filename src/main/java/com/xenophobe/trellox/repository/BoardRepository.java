package com.xenophobe.trellox.repository;

import com.xenophobe.trellox.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
    Optional<Board> findByBoardName(String boardName);
}
