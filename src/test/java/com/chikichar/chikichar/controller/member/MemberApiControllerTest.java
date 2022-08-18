package com.chikichar.chikichar.controller.member;

import com.chikichar.chikichar.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = MemberApiController.class)
class MemberApiControllerTest {
    @MockBean
    private MemberService memberService;
    private final ObjectMapper objectMapper = new ObjectMapper();




}