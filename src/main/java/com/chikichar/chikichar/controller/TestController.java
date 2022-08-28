package com.chikichar.chikichar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sample/all")
    public String all(){
        return "sample/all";
    }

    @GetMapping("/sample/member")
    public String member(){
        return "sample/member";
    }
    @GetMapping("/modify")
    public String modify() {
        return "modify";
    }
}
