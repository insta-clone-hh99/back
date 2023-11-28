package com.sparta.instaclone.domain.like;

import com.sparta.instaclone.global.secuity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 생성
    @PostMapping("/likes")
    public ResponseEntity<String> createLike(@PathVariable Long postId, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();
        likeService.createLike(userId, postId);
        return ResponseEntity.ok("좋아요");
    }

    // 좋아요 삭제
    @DeleteMapping("/likes")
    public ResponseEntity<String> deleteLike(@PathVariable Long postId, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();
        likeService.deleteLike(userId, postId);
        return ResponseEntity.ok("좋아요 취소");
    }
}
