package com.sparta.instaclone.controller;

import com.sparta.instaclone.dto.SignupRequestDto;
import com.sparta.instaclone.entity.User;
import com.sparta.instaclone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().build(); // 상태 코드 200만 반환
    }
}
