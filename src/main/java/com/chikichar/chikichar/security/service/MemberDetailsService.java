package com.chikichar.chikichar.security.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.dto.AuthMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername={}", username);
        Optional<Member> findMember = memberRepository.findByEmail(username);
        if(findMember.isEmpty()){
            log.info("usernameNotFoundException");
            throw new UsernameNotFoundException("아이디가 없습니다");
        }
        Member member = findMember.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getMemberRole().getKey()));
        AuthMemberDto authMemberDto = new AuthMemberDto(member.getEmail(),member.getPassword(),authorities);
        authMemberDto.setName(member.getName());
        return authMemberDto;
    }
}
