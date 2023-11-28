package com.sparta.instaclone.domain.post.dto;

import com.sparta.instaclone.domain.comment.dto.CommentResponseDto;
import com.sparta.instaclone.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailsResponseDto extends PostResponseDto {
    private List<CommentResponseDto> commentList; // 댓글 목록

    public PostDetailsResponseDto(Post post, List<CommentResponseDto> commentList, int likeCount) {
        super(post, commentList.size(), likeCount); // PostResponseDto 생성자 호출
        this.commentList = commentList;
    }
}
