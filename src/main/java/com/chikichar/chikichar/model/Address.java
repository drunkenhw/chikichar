package com.chikichar.chikichar.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

/**
 * city = 도시
 * street = 도로명 주소
 * zipcode = 우편번호
 */
@Getter
@ToString
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Address {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;
}
