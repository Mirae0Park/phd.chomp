package com.phd.chomp.dto;

import com.phd.chomp.constant.MemberRole;
import com.phd.chomp.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto { // Request를 받을 때 사용
    private String uid;
    private String email;
    private String pw;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .uid(uid)
                .pw(passwordEncoder.encode(pw))
                .email(email)
                .memberRole(MemberRole.USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(uid, pw);
    }
}
