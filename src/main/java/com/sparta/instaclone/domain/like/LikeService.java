package com.sparta.instaclone.domain.like;

import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.domain.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public boolean toggleLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        Optional<Like> like = likeRepository.findById(postId);

        if (Like.isPresent()) {
            likeRepository.delete(like.get());
            return false; // 좋아요 취소
        } else {
            Like newLike = like.builber().post(post).build();

            likeRepository.save(newLike);
            return true; // 좋아요 등록
        }
    }
}
