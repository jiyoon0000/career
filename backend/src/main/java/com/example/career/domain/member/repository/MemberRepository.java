package com.example.career.domain.member.repository;

import com.example.career.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "job")
    Optional<Member> findByEmail(String email);
}
