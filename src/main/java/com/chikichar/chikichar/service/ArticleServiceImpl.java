package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleImageRepository articleImageRepository;
    private final ArticleRepository articleRepository;

    @Override
    public List<NormalBoardArticleDto> printArticleList(Long boardId) {
       return articleRepository.findByBoardId(boardId);

    }
}
