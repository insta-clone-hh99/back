package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.like.Like;
import com.sparta.instaclone.domain.post.dto.PostRequestDto;
import com.sparta.instaclone.domain.post.image.Image;
import com.sparta.instaclone.domain.user.User;
import com.sparta.instaclone.global.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 500)
    private String content; // 게시물 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 게시물과 이미지는 일대다 관계
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    // 좋아요
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Post(PostRequestDto dto, List<Image> images) {
        this.content = dto.getContent();
        setImages(images); // 이미지 리스트 설정 및 Post 객체 할당

    }

    public void setImages(List<Image> images) {
        this.images = images;
        images.forEach(image -> image.setPost(this));
    }
}
