package com.phd.chomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto<E> {

    private Long id;

    private String uid; // 회원 아이디

    private String name; // 배송지명

    private String person; // 받는 분

    private String zonecode; // 우편번호

    private String address; // 주소

    private String detailAddr; // 상세주소

    private String phone; // 휴대폰 번호

    private Boolean sDefault; // 기본 배송지 여부

    private List<E> dtoList;
}
