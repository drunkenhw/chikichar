package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryQuerydsl {
    List<NormalBoardArticleDto> findByBoardId(Long boardId);

    Page<NormalBoardArticleDto> searchBoardPaging(BoardSearchType searchType, String boardName, Pageable pageable);
}
