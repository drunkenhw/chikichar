package com.chikichar.chikichar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalBoardArticleDto {

    private String nickname;
    private String boardName;
    private String title;
    private String content;
    private int viewCount;
    private int commentCount;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<ArticleImageDto> imageList = new ArrayList<>();


}
