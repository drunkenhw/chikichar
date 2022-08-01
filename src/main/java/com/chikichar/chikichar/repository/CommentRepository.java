package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
