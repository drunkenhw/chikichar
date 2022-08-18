package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.model.BoardType;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();

    Long addBoard(String boardName, BoardType boardType);
}
