package com.phd.chomp.repository;

import com.phd.chomp.dto.ShippingDto;
import com.phd.chomp.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface ShippingRepository extends JpaRepository<Shipping, Long>, QuerydslPredicateExecutor<Shipping> {

    @Transactional
    @Query("SELECT s FROM Shipping s WHERE s.member.uid = :uid AND s.sdefault != NULL")
    ShippingDto getDefaultAddr(String uid);
}
