package com.sparta.instaclone.domain.like;

import com.sparta.instaclone.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPost(Post post);
//    Optional<Like> findByPostAndUser(Post post, User user); // 추후 User 엔티티 추가 시 사용
}

