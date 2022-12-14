package com.chikichar.chikichar.controller.board;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.dto.page.CustomPageRequest;
import com.chikichar.chikichar.dto.page.CustomPageResponse;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.security.CurrentUser;
import com.chikichar.chikichar.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;

    @GetMapping("/brand")
    public String brandBoard(@CurrentUser Member member){
        if(member != null) {
            return "redirect:/board/" + member.getBrand();
        }
        return "redirect:/board/driver";
    }

    @GetMapping("/board/{boardName}")
    public String getArticleList(@ModelAttribute("searchType") BoardSearchType boardSearchType, CustomPageRequest pageRequest,
                                 Model model) {

        Page<NormalBoardArticleDto> result = articleService.pagingArticleBySearchType(boardSearchType, pageRequest.of());
        CustomPageResponse<NormalBoardArticleDto> pagingDto = new CustomPageResponse<>(result);

        model.addAttribute("articles", pagingDto);
        model.addAttribute("boardName", boardSearchType.getBoardName());

        return "board/list";

    }
}
