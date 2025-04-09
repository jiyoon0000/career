package com.example.career.domain.onboarding.repository;

import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByJob(Job job);
}
