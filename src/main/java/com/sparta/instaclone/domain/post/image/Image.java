package com.sparta.instaclone.domain.post.image;

import com.sparta.instaclone.domain.post.Post;
import com.sparta.instaclone.global.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "images")
public class Image extends Timestamped {

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

    @Builder
    public Image(String originName, String storedImagePath, Post post) {
        this.originName = originName;
        this.storedImagePath = storedImagePath;
        this.post = post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
