package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
