package com.phd.chomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class mainController {

    @GetMapping("/join")
    public String joinGet() {
        /*model.addAttribute("memberJoinDto", new MemberJoinDTO());*/

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/signup";
    }

    @GetMapping("/login")
    public String loginGet(){

        log.info("MemberController.loginGet() 로그인 처리");

        return "member/login";
    }

}
