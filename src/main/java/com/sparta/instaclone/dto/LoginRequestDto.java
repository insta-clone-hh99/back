package com.sparta.instaclone.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
//    private String userId;
    private String email;
    private String password;
}
