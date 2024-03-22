package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uid; // 아이디

    @Column(nullable = false, length = 1000)
    private String pw; // 비밀번호

    private String name; // 이름

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    private String birth; // 생년월일

    @Column(unique = true)
    private String phone; // 전화번호

    private boolean del; // 회원탈퇴


}
