package com.chikichar.chikichar.dto.member;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private String name;

    private String email;

    private String nickname;

    private String phone;

    private String city;

    private String street;

    private String zipcode;

    private Brand brand;

    private int point;

    private int articleCount;

    private int recommendCount;

}
