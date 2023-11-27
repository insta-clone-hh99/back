package com.sparta.instaclone.domain.comment;

import com.sparta.instaclone.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    // 게시물 ID에 따라 댓글의 수를 조회
    int countByPost(Post post);

    // 게시물에 연관된 모든 댓글 삭제
    void deleteByPost(Post post);
}
