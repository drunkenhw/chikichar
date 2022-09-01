package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;


/**
 * id = 회원 번호 (index)
 * email = 회원 email < 로그인 시 사용 (아이디)
 * memberRole = 일반, 업체, 관리자
 * name = 일반 회원은 자기이름, 업체 회원은 업체 이름 사용
 * point = 회원 포인트 (할인 가능)
 */

@Entity
@Getter
@ToString(exclude = {"articles", "comments", "recommends"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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

    @Enumerated(STRING)
    private Brand brand;

    private int point;

    @Column(nullable = false)
    @Enumerated(STRING)
    private MemberRole memberRole;

    @Enumerated(STRING)
    private SocialType socialType;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Recommend> recommends = new ArrayList<>();

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

    public void modifyMember(String nickname, String phone, Address address, Brand brand, MemberRole memberRole) {
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.brand = brand;
        this.memberRole = memberRole;

    }
    public void OAuth2ModifyMember(String name,String nickname, String phone, Address address, Brand brand, MemberRole memberRole) {
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.brand = brand;
        this.memberRole = memberRole;

    }


    public void changePassword(String password) {
        this.password = password;
    }

    public void pointUp() {
        this.point += 1;
    }

    public void pointDown() {
        this.point -= 1;
    }

    public void ban() {
        this.memberRole = MemberRole.BAN;
    }

}
