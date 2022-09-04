package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryQuerydsl {

    Page<NormalBoardArticleDto> searchBoardPaging(BoardSearchType searchType, Pageable pageable);
}
