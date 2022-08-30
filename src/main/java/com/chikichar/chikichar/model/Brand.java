package com.chikichar.chikichar.model;

import lombok.Getter;

@Getter
public enum Brand {
    HYUNDAI("현대"),
    SSANGYONG("쌍용"),
    CHEVROLET("쉐보레"),
    BENZ("BENZ"),
    BMW("BMW"),
    AUDI("AUDI"),
    ETC("기타")
    ;

    private String value;

    Brand(String value){
        this.value = value;
    }
}
