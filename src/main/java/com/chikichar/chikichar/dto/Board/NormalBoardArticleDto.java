package com.chikichar.chikichar.dto.Board;

import com.chikichar.chikichar.entity.ArticleImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class NormalBoardArticleDto {

    private String nickname;
    private String title;
    private int viewCount;
    private Long commentCount;
    private LocalDateTime regDate;
    private String imagePath;

    private Long recommendCount;
    @QueryProjection
    public NormalBoardArticleDto(String nickname, String title, int viewCount, Long commentCount, LocalDateTime regDate, String imagePath, Long recommendCount) {
        this.nickname = nickname;
        this.title = title;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.regDate = regDate;
        this.imagePath = imagePath;
        this.recommendCount = recommendCount;
    }
}
