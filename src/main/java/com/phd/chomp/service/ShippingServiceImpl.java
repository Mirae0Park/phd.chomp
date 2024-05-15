package com.phd.chomp.service;

import com.phd.chomp.dto.ShippingDto;
import com.phd.chomp.entity.Member;
import com.phd.chomp.repository.MemberRepository;
import com.phd.chomp.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShippingServiceImpl implements ShippingService{

    private final MemberRepository memberRepository;
    private final ShippingRepository shippingRepository;

    @Override
    public ShippingDto getDefaultAddr(String uid) {

        Member member = memberRepository.findWithRoleSetByUid(uid);

        if (member != null){
            ShippingDto shippingDto = shippingRepository.getDefaultAddr(uid);
            return shippingDto;
        } else {
            throw new RuntimeException("해당 회원의 기본 배송지 정보를 찾을 수 없습니다.");
        }
    }
}
