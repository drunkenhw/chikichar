package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

    @Test
    public void getMemberList(){
        List<Member> memberList = adminService.getMemberList();
        for (Member member : memberList) {
            System.out.println("member = " + member);
        }
    }
}