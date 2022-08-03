package com.chikichar.chikichar.member.repository;

import com.chikichar.chikichar.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
