package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Member;

public interface CommentService {

    Long writeComment(Member member, Article article);
}
