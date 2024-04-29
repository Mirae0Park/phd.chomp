package com.phd.chomp.repository;

import com.phd.chomp.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUid(String uid);

    @EntityGraph(attributePaths = "memberRole")
    Member findWithRoleSetByUid(String uid);

    boolean existsByUid(String uid); // 중복 가입 방지
}
