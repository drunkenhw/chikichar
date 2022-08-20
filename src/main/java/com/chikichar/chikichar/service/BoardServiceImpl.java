package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public Long addBoard(String boardName, BoardType boardType) {
        Board board = new Board(boardName, boardType);
        Board saveBoard = boardRepository.save(board);
        return saveBoard.getId();
    }
}
