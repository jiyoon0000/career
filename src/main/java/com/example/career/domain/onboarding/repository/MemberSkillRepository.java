package com.example.career.domain.onboarding.repository;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.onboarding.entity.MemberSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {

    List<MemberSkill> findByMember(Member member);

    void deleteByMember(Member member);
}
