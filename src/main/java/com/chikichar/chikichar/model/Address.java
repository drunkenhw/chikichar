package com.chikichar.chikichar.model;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * city = 도시
 * street = 도로명 주소
 * zipcode = 우편번호
 */
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Address {

    private String city;

    private String street;

    private String zipcode;
}
