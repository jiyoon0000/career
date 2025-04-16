package com.example.career.domain.onboarding.service;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.domain.onboarding.dto.SkillRecommendResponseDto;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final MemberRepository memberRepository;

    public List<SkillRecommendResponseDto> recommendSkills(MemberDetails user) {
        Member member = memberRepository.findById(user.getMember().getId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        String jobName = member.getJob() != null ? member.getJob().getName() : null;

        if (jobName == null) {
            throw new BadRequestException(ErrorCode.JOB_NOT_SELECTED);
        }

        PromptTemplate promptTemplate = new PromptTemplate("""
                    '{job}' 직무에 필요한 스킬 20가지를 추천해줘.
                
                    단, 아래 5개의 협업 도구 스킬은 반드시 포함하고,
                    나머지 15개는 해당 직무에 필요한 기술 스택(프로그래밍 언어, 프레임워크, 툴 등 실무 중심 역량) 위주로 구성해줘.
                    책임감, 성실성, 커뮤니케이션 같은 태도/성향은 포함하지 마.
                
                    그리고, 직무가 사무직 또는 IT 관련 직군인 경우에만 다음의 협업 툴을 포함하고,
                    그 외 직무(비사무직, 요리사, 간호사, 미용사, 생산직, 제조직 등)에서는 해당 협업 툴은 제외하고, 직무에 적합한 실무 기술로만 구성해줘.
                
                    - Notion
                    - Jira
                    - Slack
                    - GitHub
                    - Figma
                
                    출력 형식은 다음과 같이 숫자와 스킬 이름만 줄 단위로 정리해줘:
                    1. Notion
                    2. Jira
                    ...
                    20. React
                
                    스킬 이름만 포함하고 설명은 절대 붙이지 말고, 줄마다 한 줄씩 정확하게 20개 작성해줘.
                """);

        promptTemplate.add("job", jobName);

        String content = chatClient.call(promptTemplate.create())
                .getResult().getOutput().getContent();

        return Arrays.stream(content.split("\n"))
                .map(s -> s.replaceAll("^[0-9.\\-\\s]+", "").trim())
                .filter(s -> !s.isBlank())
                .map(SkillRecommendResponseDto::new)
                .collect(Collectors.toList());
    }
}
