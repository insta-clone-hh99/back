package com.sparta.instaclone.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto postRequestDto) {
        // 서비스 계층에 DTO와 이미지를 전달하여 처리
        PostResponseDto postResponse = postService.createPost(postRequestDto);
        return ResponseEntity.ok(postResponse);
    }

    // 게시물 전체 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        PostListResponseDto response = new PostListResponseDto(posts);
        return ResponseEntity.ok(response);
    }
}
