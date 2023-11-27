package com.sparta.instaclone.domain.comment.dto;

import com.sparta.instaclone.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String userName;
    private String comment;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userName = comment.getUser().getUserName(); // User 엔티티의 getUsername 메서드
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
    }

}
