package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  id = 회원 번호 (index)
 *  email = 회원 email < 로그인 시 사용
 *  memberRole = 일반, 업체, 관리자
 *  name = 일반 회원은 자기이름, 업체 회원은 업체 이름 사용
 *  point = 회원 포인트 (할인 가능)
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String name;

    private String password;

    private int point;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Recommend> recommends = new ArrayList<>();

    @Builder
    public Member(String email, MemberRole memberRole, String name, String password, Address address) {
        this.email = email;
        this.memberRole = memberRole;
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public void pointUp(){
        this.point += 1;
    }
}
