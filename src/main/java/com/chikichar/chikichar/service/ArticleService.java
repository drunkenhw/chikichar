package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    public Page<NormalBoardArticleDto> printArticleList(BoardSearchType boardSearchType, String boardName, Pageable pageable);
}
