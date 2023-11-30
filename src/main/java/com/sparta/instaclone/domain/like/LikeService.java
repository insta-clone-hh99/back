package com.sparta.instaclone.domain.like;

import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.domain.post.PostRepository;
import com.sparta.instaclone.domain.user.User;
import com.sparta.instaclone.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("없는 사용자 입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        // 이미 좋아요를 했는지 확인
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            // 이미 좋아요를 한 경우, 추가적인 동작을 하지 않음
            return;
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("없는 사용자 입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        Optional<Like> likeOpt = likeRepository.findByUserAndPost(user, post);
        if (likeOpt.isPresent()) {
            likeRepository.delete(likeOpt.get());
        } else {
            throw new EntityNotFoundException("좋아요 기록이 없습니다.");
        }
    }
}
