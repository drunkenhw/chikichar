package com.chikichar.chikichar.member.dto;

import com.chikichar.chikichar.member.domain.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "공백은 허용하지 않습니다.")
    private String name;

    @NotBlank(message = "공백은 허용하지 않습니다.")
    @Email
    private String email;

    @NotBlank(message = "공백은 허용하지 않습니다.")
    private String password;

    @NotBlank(message = "공백은 허용하지 않습니다.")
    private String nickname;

    @NonNull
    private Address address;

    @NonNull
    private String phone;

    @NonNull
    private Brand brand;

    private int point;

    @NotNull
    private MemberRole memberRole;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .password(password)
                .brand(brand)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .address(address)
                .memberRole(memberRole).build();
    }

}
