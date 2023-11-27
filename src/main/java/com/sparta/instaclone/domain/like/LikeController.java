package com.sparta.instaclone.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<String> createLike(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.createLike(userId, postId);
        return ResponseEntity.ok("좋아요");
    }

    @DeleteMapping("/likes")
    public ResponseEntity<String> deleteLike(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.deleteLike(userId, postId);
        return ResponseEntity.ok("좋아요 취소");
    }
}
