package com.sparta.instaclone.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank (message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식이 옳바르지 않습니다.")
    private String email;

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$",
            message = "비밀번호는 4~12자리의 영문 대소문자와 숫자의 조합이어야 합니다.")
    private String password;

    @NotBlank(message = "이름을 정확히 작성해주세요")
    private String userName;

    @Size(min = 4, max = 12)
    private String nickname;
}
