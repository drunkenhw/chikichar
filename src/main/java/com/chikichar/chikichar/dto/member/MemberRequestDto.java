package com.chikichar.chikichar.dto.member;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.SocialType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.chikichar.chikichar.model.MemberRole.toEnum;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "Email은 필수 입력 항목입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;

    private String city;

    private String street;

    private String zipcode;

    private Brand brand;

    private String memberRole;

    public Address getAddress(){
        return new Address(city, street, zipcode);
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .password(password)
                .brand(brand)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .address(new Address(city, street, zipcode))
                .memberRole(MemberRole.valueOf(memberRole))
                .socialType(SocialType.LOCAL)
                .build();
    }

}
