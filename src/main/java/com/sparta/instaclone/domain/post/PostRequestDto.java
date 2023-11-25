package com.sparta.instaclone.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String content;
    private List<MultipartFile> images; // 이미지 파일을 받기 위한 MultipartFile 필드 추가
}
