package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.post.dto.*;
import com.sparta.instaclone.global.secuity.UserDetailsImpl;
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
                                                      Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId(); // 현재 인증된 사용자의 ID

        PostResponseDto postResponse = postService.createPost(postRequestDto, userId);
        return ResponseEntity.ok(postResponse);
    }

    // 게시물 전체 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(new PostListResponseDto(posts));
    }

    // 특정 게시물 상세 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailsResponseDto> getPostDetails(@PathVariable Long postId) {
        PostDetailsResponseDto postDetail = postService.getPostDetails(postId);
        return ResponseEntity.ok(postDetail);
    }

    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId,
                                                      @ModelAttribute PostUpdateDto postUpdateDto,
                                                      Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId(); // 현재 인증된 사용자의 ID

        PostResponseDto updatedPost = postService.updatePost(postId, postUpdateDto, userId);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,
                                             Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId(); // 현재 인증된 사용자의 ID

        postService.deletePost(postId, userId);
        return ResponseEntity.ok("삭제 완료");
    }
}
