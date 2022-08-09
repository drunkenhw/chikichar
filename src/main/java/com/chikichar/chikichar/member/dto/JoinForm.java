package com.chikichar.chikichar.member.dto;

import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinForm {

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

    @NotBlank
    private MemberRole memberRole;
}
