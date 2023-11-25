package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.post.image.Image;
import com.sparta.instaclone.global.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 500)
    private String content; // 게시물 내용

    // 게시물과 이미지는 일대다 관계를 가집니다.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    public Post(PostRequestDto dto, List<Image> images) {
        this.content = dto.getContent();
        this.images = images;
    }
}
