package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.post.image.Image;
import com.sparta.instaclone.domain.post.image.ImageS3Service;
import lombok.RequiredArgsConstructor;
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
        // 이미지 업로드
        List<Image> uploadedImages = imageS3Service.uploadImages(postRequestDto.getImages());

        // Post 객체 생성. 이때 이미지들이 포함
        Post post = new Post(postRequestDto, uploadedImages);

        // Post 저장
        Post savedPost = postRepository.save(post);

        return new PostResponseDto(savedPost);
    }

    // 게시물 조회
    public List<PostResponseDto> getAllPosts() {
        // 모든 게시물 조회 후 DTO로 변환하여 반환
        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시물 상세 조회
//    public PostListResponseDto getPostByUserId
}
