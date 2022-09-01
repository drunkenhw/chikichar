package com.chikichar.chikichar.dto;

import com.chikichar.chikichar.entity.ArticleImage;
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
    private String title;
    private String content;
    private int viewCount;
    private Long commentCount;
    private LocalDateTime regDate;
    private ArticleImage articleImage;


}
