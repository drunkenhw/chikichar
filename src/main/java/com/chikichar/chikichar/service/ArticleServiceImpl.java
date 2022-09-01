package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.ArticleImage;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.custom.ArticleQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleImageRepository articleImageRepository;
    private final ArticleQuerydslRepository articleQuerydslRepository;

    @Override
    public List<NormalBoardArticleDto> printArticleList(Long boardId) {
       return articleQuerydslRepository.findByBoardId(boardId);

    }
}
