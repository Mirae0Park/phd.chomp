package com.phd.chomp.controller;

import com.phd.chomp.dto.MemberResponseDto;
import com.phd.chomp.service.MemberService;
import com.phd.chomp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById(){
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByUid(@PathVariable String uid){
        return ResponseEntity.ok(memberService.findMemberInfoByUid(uid));
    }

}
