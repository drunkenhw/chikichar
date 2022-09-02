package com.chikichar.chikichar.controller.board;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;

    @GetMapping("/list/{boardNum}")
    public String getArticleList(@PathVariable Long boardNum, Model model){
        List<NormalBoardArticleDto> dto = articleService.printArticleList(boardNum);
        log.info("dto={}",dto);
        model.addAttribute("articles", dto);
        return "board/list";
    }
}
