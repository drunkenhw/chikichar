package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.model.*;
import lombok.*;

import javax.persistence.*;


/**
 *  id = 회원 번호 (index)
 *  email = 회원 email < 로그인 시 사용 (아이디)
 *  memberRole = 일반, 업체, 관리자
 *  name = 일반 회원은 자기이름, 업체 회원은 업체 이름 사용
 *  point = 회원 포인트 (할인 가능)
 */

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    private int point;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    @Builder
    public Member(String email, String name, String password, String nickname, String phone, Address address, Brand brand, MemberRole memberRole, SocialType socialType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.brand = brand;
        this.memberRole = memberRole;
        this.socialType = socialType;
    }

    public void modifyMember( String nickname, String phone, Address address, Brand brand){
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.brand = brand;

    }


    public void changePassword(String password){
        this.password = password;
    }
    public void pointUp(){
        this.point += 1;
    }
    public void pointDown() { this.point -= 1;}

    public void ban(){
        this.memberRole = MemberRole.BAN;
    }
}
