package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String boardName);
}

