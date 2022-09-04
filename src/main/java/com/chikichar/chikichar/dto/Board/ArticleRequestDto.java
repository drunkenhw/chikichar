package com.chikichar.chikichar.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {

    private String title;
    private String content;
    private String filePath;
    private String boardName;
    private String streetAddress;
    private String detailAddress;
    private String zipcode;
    private Double locationX;
    private Double locationY;
}
