package com.example.career.domain.onboarding.repository;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.onboarding.entity.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {

    Optional<Onboarding> findByMember(Member member);

    boolean existsByMember(Member member);
}
