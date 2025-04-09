package com.example.career.domain.onboarding.repository;

import com.example.career.domain.onboarding.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
