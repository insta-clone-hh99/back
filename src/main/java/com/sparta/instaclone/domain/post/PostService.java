package com.sparta.instaclone.domain.post;

import com.sparta.instaclone.domain.comment.CommentRepository;
import com.sparta.instaclone.domain.comment.dto.CommentResponseDto;
import com.sparta.instaclone.domain.like.LikeRepository;
import com.sparta.instaclone.domain.post.dto.PostDetailsResponseDto;
import com.sparta.instaclone.domain.post.dto.PostRequestDto;
import com.sparta.instaclone.domain.post.dto.PostResponseDto;
import com.sparta.instaclone.domain.post.dto.PostUpdateDto;
import com.sparta.instaclone.domain.post.image.Image;
import com.sparta.instaclone.domain.post.image.ImageS3Service;
import com.sparta.instaclone.domain.user.User;
import com.sparta.instaclone.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageS3Service imageS3Service;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long userId) {
        // User 객체 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("없는 사용자 입니다."));

        // Post 객체 생성
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setUser(user); // User 객체 설정
        postRepository.save(post); // Post 객체를 먼저 저장

        // 이미지 업로드 및 Image 엔티티 리스트 생성
        List<Image> uploadedImages = imageS3Service.uploadImages(postRequestDto.getImages(), post);

        // Post 객체에 이미지 리스트 연결
        post.setImages(uploadedImages);

        // Post 저장
        Post savedPost = postRepository.save(post);

        // 게시물에 대한 댓글 수 조회 (새로 생성된 게시물이므로 0)
        int commentCount = 0;

        // 게시물에 대한 좋아요 수 조회 (새로 생성된 게시물이므로 0)
        int likeCount = 0;

        return new PostResponseDto(savedPost, commentCount, likeCount);
    }

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            int commentCount = commentRepository.countByPost(post); // 댓글 수
            int likeCount = likeRepository.countByPost(post);  // 좋아요 수
            return new PostResponseDto(post, commentCount, likeCount);
        }).collect(Collectors.toList());
    }

    // 특정 게시물 상세 조회
    @Transactional(readOnly = true)
    public PostDetailsResponseDto getPostDetails(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        // 해당 게시물의 댓글 목록 조회 및 DTO 변환
        List<CommentResponseDto> commentDtos = commentRepository.findByPost(post).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        int likeCount = likeRepository.countByPost(post);

        // 게시물 상세 정보와 댓글 목록을 포함하는 DTO 반환
        return new PostDetailsResponseDto(post, commentDtos, likeCount);
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateDto postUpdateDto, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        post.setContent(postUpdateDto.getContent());

        // 기존 이미지 처리
        handleExistingImages(post);

        // 새로운 이미지 업로드
        List<Image> newImages = imageS3Service.uploadImages(postUpdateDto.getImages(), post);
        post.setImages(newImages);

        Post updatedPost = postRepository.save(post);

        // 수정된 게시물의 댓글 수 조회
        int commentCount = commentRepository.countByPost(updatedPost);

        // 수정된 게시물의 좋아요 수 조회
        int likeCount = likeRepository.countByPost(updatedPost);


        return new PostResponseDto(updatedPost, commentCount, likeCount);
    }


    // 게시물 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시물 입니다."));

        // 연관된 댓글 삭제
        commentRepository.deleteByPost(post);

        // 연관된 이미지 삭제
        List<Image> images = post.getImages();
        images.forEach(imageS3Service::deleteImage);

        // 게시물 삭제
        postRepository.delete(post);
    }

    // 수정, 삭제시 이미지 관리
    private void handleExistingImages(Post post) {
        List<Image> existingImages = post.getImages();
        // 기존 이미지 삭제
        existingImages.forEach(imageS3Service::deleteImage);
        // 기존 이미지 리스트를 비움
        post.getImages().clear();
    }
}
