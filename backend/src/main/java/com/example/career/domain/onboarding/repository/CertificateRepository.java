package com.example.career.domain.onboarding.repository;

import com.example.career.domain.onboarding.entity.Certificate;
import com.example.career.domain.onboarding.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByJob(Job job);
}
