package com.sparta.instaclone.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Boolean liked = LikeService.toggleLike(postId);
        return ResponseEntity.ok(Map.of("liked", liked));
    }
}
