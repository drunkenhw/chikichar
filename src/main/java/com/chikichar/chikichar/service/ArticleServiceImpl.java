package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleImageRepository articleImageRepository;
    private final ArticleRepository articleRepository;

    @Override
    public Page<NormalBoardArticleDto> printArticleList(BoardSearchType boardSearchType, Pageable pageable) {
       return articleRepository.searchBoardPaging(boardSearchType,pageable);

    }
}
