package com.sparta.instaclone.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private String content;
    private List<MultipartFile> images; // 수정할 이미지들
}

