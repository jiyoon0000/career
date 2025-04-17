package com.example.career.domain.member.dto;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.enums.Gender;
import com.example.career.global.util.ValidationPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = ValidationPatterns.PASSWORD_REGEX,
            message = "비밀번호는 영문 소문자, 숫자, 특수문자를 포함한 8~12자여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    @Pattern(regexp = ValidationPatterns.BIRTH_REGEX,
            message = "생년월일은 8자리 숫자(YYMMDD) 형식이어야 합니다.")
    private String birth;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = ValidationPatterns.PHONE_NUMBER_REGEX,
            message = "전화번호 형식은 010-xxxx-xxxx 이어야 합니다.")
    private String phone;

    private Gender gender;

    public Member toEntity(String encodedPassword) {
        return new Member(
                this.email,
                encodedPassword,
                this.name,
                this.birth,
                this.phone,
                this.gender
        );
    }

}
