package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="shipping")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String delAddress; // 배송지명

    private String recipient; // 받는 사람

    private String zonecode; // 우편번호

    private String address; // 주소

    private String DetailAddr; // 상세주소

    private String sphone; // 전화번호

    private boolean sdefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

}
