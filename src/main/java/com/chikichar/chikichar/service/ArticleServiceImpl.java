package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.ArticleRequestDto;
import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    @Override
    public Page<NormalBoardArticleDto> pagingArticleBySearchType(BoardSearchType boardSearchType, Pageable pageable) {
        return articleRepository.searchBoardPaging(boardSearchType, pageable);

    }

    @Transactional
    @Override
    public Long writeArticle(Member member, ArticleRequestDto dto) {
        //TODO Exception Custom
        Board board = boardRepository.findByName(dto.getBoardName())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다."));

        Article article = dtoToArticle(member, dto, board);

        articleRepository.save(article);

        return article.getId();
    }

    private Article dtoToArticle(Member member, ArticleRequestDto dto, Board board) {
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .board(board)
                .member(member)
                .address(
                        new Address(dto.getStreetAddress(), dto.getDetailAddress(), dto.getZipcode()))
                .locationX(dto.getLocationX())
                .locationY(dto.getLocationY())
                .build();
        return article;
    }
}
