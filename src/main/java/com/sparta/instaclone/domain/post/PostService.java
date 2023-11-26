package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.post.image.Image;
import com.sparta.instaclone.domain.post.image.ImageS3Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageS3Service imageS3Service;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        // Post 객체 생성
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        postRepository.save(post); // Post 객체를 먼저 저장

        // 이미지 업로드 및 Image 엔티티 리스트 생성
        List<Image> uploadedImages = imageS3Service.uploadImages(postRequestDto.getImages(), post);

        // Post 객체에 이미지 리스트 연결
        post.setImages(uploadedImages);

        // Post 저장
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostDetails(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        // 지연 로딩이 설정된 경우, Hibernate.initialize를 사용하여 이미지를 로드
        Hibernate.initialize(post.getImages());

        return new PostResponseDto(post);
    }

    // 게시물 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(post -> Hibernate.initialize(post.getImages()));
        // 모든 게시물 조회 후 DTO로 변환하여 반환
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시물 상세 조회
//    public PostListResponseDto getPostByUserId
}
