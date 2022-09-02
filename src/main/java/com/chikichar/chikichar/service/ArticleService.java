package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;

import java.util.List;

public interface ArticleService {

    List<NormalBoardArticleDto> printArticleList(Long boardId);
}
