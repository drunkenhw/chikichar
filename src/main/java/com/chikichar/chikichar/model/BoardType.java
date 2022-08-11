package com.chikichar.chikichar.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoardType {
    NORMAL("일반게시판"),
    MAP("지도게시판"),
    MARKET("장터게시판")
    ;

    private String value;
}
