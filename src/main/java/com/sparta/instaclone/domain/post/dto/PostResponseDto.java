package com.sparta.instaclone.domain.post.dto;

import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.domain.post.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
//    private String userName;
    private String contents;
    private List<String> imageUrls;
    private int commentCount; // 댓글 수
    private int likeCount; // 좋아요 수
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post, int commentCount, int likeCount) {
        this.postId = post.getPostId();
//        this.userName = post.getUser().getUsername(); // User 엔티티의 getUsername 메서드
        this.contents = post.getContent();
        this.commentCount = commentCount;
        this.likeCount = likeCount; // 좋아요 수 설정
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.imageUrls = post.getImages().stream()
                .map(Image::getStoredImagePath) // Image 객체에서 URL 추출
                .collect(Collectors.toList());
    }
}
