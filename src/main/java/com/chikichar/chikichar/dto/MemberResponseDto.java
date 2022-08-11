package com.chikichar.chikichar.dto;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Comment;
import com.chikichar.chikichar.entity.Recommend;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;

import java.util.List;


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



    private List<Comment> commentList;

    private List<Recommend> recommendList;
}
