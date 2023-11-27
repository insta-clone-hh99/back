package com.sparta.instaclone.domain.like;

import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPost(Post post);
    Optional<Like> findByUserAndPost(User user, Post post);
}

