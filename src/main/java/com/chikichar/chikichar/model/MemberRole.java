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
    ADMIN("ROLE_ADMIN","관리자"),
    BAN("ROLE_BAN","정지회원"),
    UN_MODIFY("ROLE_UNMODIFY","회원정보 미기입")
    ;
    private String key;
    private String value;

    public static MemberRole toEnum(String memberRole){
        return MemberRole.valueOf(memberRole);
    }
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}



