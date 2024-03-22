package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notify")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notify extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ncontent; // 내용

    private String url;

    private boolean isRead; // 읽음 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private Member receiver; // 받는 사람

}
