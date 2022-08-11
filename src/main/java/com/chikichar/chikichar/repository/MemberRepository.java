package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    @Query("select m.email from Member m where m.name=:name and m.phone=:phone")
    Optional<String> findEmailByNameAndPhone(@Param("name") String name,@Param("phone") String phone);
}
