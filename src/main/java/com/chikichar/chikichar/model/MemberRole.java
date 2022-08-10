package com.chikichar.chikichar.model;



/**
 * USER = 일반 유저
 * COMPANY = 업체
 * ADMIN = 관리자
 */

public enum MemberRole {
    USER("일반회원"),
    COMPANY("업체회원"),
    ADMIN("관리자")
    ;

    private String value;

    MemberRole(String value){
        this.value = value;
    }

}



