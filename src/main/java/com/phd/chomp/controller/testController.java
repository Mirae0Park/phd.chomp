package com.phd.chomp.controller;

import com.phd.chomp.dto.MemberRequestDto;
import com.phd.chomp.dto.MemberResponseDto;
import com.phd.chomp.dto.TokenDto;
import com.phd.chomp.dto.TokenRequestDto;
import com.phd.chomp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Log4j2
public class testController {

    @GetMapping("/join")
    public String joinGet() {
        /*model.addAttribute("memberJoinDto", new MemberJoinDTO());*/

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/signup";
    }

}
