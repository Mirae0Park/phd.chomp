package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="answer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acontent; // 내용

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Question question; // 질문 하나 당 답변 한 개
}
