package com.chikichar.chikichar.security.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByEmail(username);
        if(findMember.isEmpty()){
            throw new UsernameNotFoundException("이메일을 확인하세요");
        }
        return UserPrincipal.create(findMember.get());
    }
}
