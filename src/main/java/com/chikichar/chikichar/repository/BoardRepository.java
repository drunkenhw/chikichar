package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
