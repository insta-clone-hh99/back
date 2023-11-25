package com.sparta.instaclone.domain.post.image;

import com.sparta.instaclone.domain.post.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String originName; // 원본 파일명

    @Column(nullable = false)
    private String storedImagePath; // 저장된 파일 경로 (S3 URL)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 관련된 게시물

    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Column
    private LocalDateTime modifiedAt; // 수정 날짜

    @Builder
    public Image(Long imageId, String originName, String storedImagePath, Post post) {
        this.imageId = imageId;
        this.originName = originName;
        this.storedImagePath = storedImagePath;
        this.post = post;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
