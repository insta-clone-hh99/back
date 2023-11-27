package com.sparta.instaclone.domain.user;

import com.sparta.instaclone.domain.user.dto.SignupRequestDto;
//import com.sparta.instaclone.domain.user.UserEnum;
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
    public User signup(SignupRequestDto requestDto) {
//        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userName = requestDto.getUserName();
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        // userName이 허용된 사용자 명단에 있는지 확인
//        if (!UserEnum.contains(userName)) {
//            throw new IllegalArgumentException("가입할 수 없는 사용자 이름입니다.");
//        }

//        // userId 중복 확인
//        if (userRepository.findByUserId(userId).isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
//            }

        // userName 중복 확인
        if (userRepository.findByUserName(userName).isPresent()) {
            throw new IllegalArgumentException("가입한 사용자가 존재합니다.");
        }

        // 사용자 정보 저장
        User user = User.builder()
//              .userId(userId)
                .password(password)
                .userName(userName)
                .email(email)
                .nickname(nickname)
                .build();
        userRepository.save(user);
        return user;
    }

}
