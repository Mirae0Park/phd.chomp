package com.phd.chomp.dto;

import com.phd.chomp.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto { // Response를 보낼 때 사용
    private String uid;
    private String email;

    public static MemberResponseDto of(Member member){
        return MemberResponseDto.builder()
                .uid(member.getUid())
                .email(member.getEmail())
                .build();
    }
}
