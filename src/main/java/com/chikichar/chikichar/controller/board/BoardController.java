package com.chikichar.chikichar.controller.board;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.dto.page.CustomPageRequest;
import com.chikichar.chikichar.dto.page.CustomPageResponse;
import com.chikichar.chikichar.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String getArticleList(BoardSearchType boardSearchType, CustomPageRequest pageRequest,Model model) {
        Page<NormalBoardArticleDto> result = articleService.printArticleList(boardSearchType, "드라이브코스", pageRequest.of());
        CustomPageResponse<NormalBoardArticleDto> pagingDto = new CustomPageResponse<>(result);
        log.info("pagingDto={}",pagingDto);
        model.addAttribute("articles", pagingDto);
        return "board/list";

    }
}
