package com.phd.chomp.repository;

import com.phd.chomp.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key); // Member ID 값으로 토큰을 가져옴
}
