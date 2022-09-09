package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.ArticleRequestDto;
import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {

   Page<NormalBoardArticleDto> pagingArticleBySearchType(BoardSearchType boardSearchType, Pageable pageable);

   Long writeArticle(Member member, ArticleRequestDto articleRequestDto);
}
