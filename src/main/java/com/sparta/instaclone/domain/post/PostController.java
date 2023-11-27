package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal Long userId) {
        // 서비스 계층에 DTO와 이미지를 전달하여 처리
        PostResponseDto postResponse = postService.createPost(postRequestDto, userId);
        return ResponseEntity.ok(postResponse);
    }

    // 게시물 전체 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        PostListResponseDto response = new PostListResponseDto(posts);
        return ResponseEntity.ok(response);
    }

    // 특정 게시물 상세 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailsResponseDto> getPostDetails(@PathVariable Long postId) {
        PostDetailsResponseDto postDetail = postService.getPostDetails(postId);
        return ResponseEntity.ok(postDetail);
    }

    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @ModelAttribute PostUpdateDto postUpdateDto) {
        PostResponseDto updatedPost = postService.updatePost(postId, postUpdateDto);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("삭제 완료");
    }
}
