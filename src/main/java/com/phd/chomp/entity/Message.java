package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Message{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender; // 송신자 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver; // 수신자 번호

    private String content; // 내용

    @CreationTimestamp // 현재 시간 자동 삽입
    private LocalDateTime sendTime; // 송신 시간
    @ColumnDefault("FALSE")
    private boolean readStatus; // 읽음 여부
}
