package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="question")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qtitle; // 제목

    private String qcontent; // 내용

    private String qcate; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;
}
