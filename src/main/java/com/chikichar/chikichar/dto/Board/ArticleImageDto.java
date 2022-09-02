package com.chikichar.chikichar.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleImageDto {
    private String name;
    private String uuid;
    private String path;
}
