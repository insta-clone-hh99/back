package com.sparta.instaclone.domain.comment;

import com.sparta.instaclone.domain.comment.dto.CommentRequestDto;
import com.sparta.instaclone.domain.comment.dto.CommentResponseDto;
import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.domain.post.PostRepository;
import com.sparta.instaclone.domain.user.User;
import com.sparta.instaclone.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("없는 사용자 입니다."));

        Comment comment = new Comment();
        comment.setComment(requestDto.getComment());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    // 댓글 목록 조회
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        // 게시물 존재 여부 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        // 해당 게시물의 댓글 조회
        List<Comment> comments = commentRepository.findByPost(post);

        // 댓글을 DTO로 변환
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }


    // 댓글 삭제
    public void deleteComment(Long commentId) {
        // 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 없습니다."));

        // 댓글 삭제
        commentRepository.delete(comment);
    }

}
