package com.sparta.instaclone.domain.post;

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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
//        this.userName = post.getUser().getUsername(); // User 엔티티의 getUsername 메서드
        this.contents = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.imageUrls = post.getImages().stream()
                .map(Image::getStoredImagePath) // Image 객체에서 URL 추출
                .collect(Collectors.toList());
    }
}
