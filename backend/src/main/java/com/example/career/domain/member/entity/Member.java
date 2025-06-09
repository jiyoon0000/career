package com.example.career.domain.member.entity;

import com.example.career.domain.oauth.enums.AuthProvider;
import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.entity.Onboarding;
import com.example.career.global.common.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "members")
public class Member extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Onboarding onboarding;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member kakaoUser(String email) {
        Member member = new Member();
        member.email = email;
        member.authProvider = AuthProvider.KAKAO;
        member.password = null;
        return member;
    }

    public void updatePassword(String newEncodedPassword) {
        this.password = newEncodedPassword;
    }

    public void selectJob(Job job) {
        this.job = job;
    }

}
