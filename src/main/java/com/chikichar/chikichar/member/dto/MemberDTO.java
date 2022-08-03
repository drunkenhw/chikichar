package com.chikichar.chikichar.member.dto;

import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String email;

    private MemberRole memberRole;

    private String name;

    private String password;

    private int point;

    private Address address;
}
