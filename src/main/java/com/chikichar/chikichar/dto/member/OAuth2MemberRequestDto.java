package com.chikichar.chikichar.dto.member;

import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2MemberRequestDto {


    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;

    private String city;

    private String street;

    private String zipcode;
    @NotNull(message = "선호 브랜드는 필수 선택 항목입니다.")
    private Brand brand;

}
