package com.example.career.domain.onboarding.repository;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.onboarding.entity.MemberCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCertificateRepository extends JpaRepository<MemberCertificate, Long> {

    List<MemberCertificate> findByMember(Member member);

    void deleteByMember(Member member);
}
