package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {

    List<Member> getMemberList();
}
