package com.sparta.instaclone.domain.user;

import com.sparta.instaclone.domain.user.dto.SignupRequestDto;
//import com.sparta.instaclone.domain.user.UserEnum;
import com.sparta.instaclone.domain.user.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userName = requestDto.getUserName();
        String nickname = requestDto.getNickname();

        // 이메일 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일 주소입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        // 사용자 정보 저장
        User user = User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .nickname(nickname)
                .build();
        User savedUser = userRepository.save(user);

        // 사용자 정보 반환
        return new SignupResponseDto(savedUser.getUserId(), savedUser.getEmail(), savedUser.getUserName(), savedUser.getNickname());
    }

}
