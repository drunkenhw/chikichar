package com.chikichar.chikichar.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * USER = 일반 유저
 * COMPANY = 업체
 * ADMIN = 관리자
 */

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER("ROLE_USER","일반회원"),
    COMPANY("ROLE_COMPANY","업체회원"),
    ADMIN("ROLE_ADMIN","관리자")
    ;
    private String key;
    private String value;


}



