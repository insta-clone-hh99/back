package com.sparta.instaclone.domain.post.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.instaclone.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageS3Service{

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름

    // MultipartFile 리스트를 받아 S3에 업로드하고 Image 엔티티 리스트로 변환
    public List<Image> uploadImages(List<MultipartFile> multipartFiles, Post post) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return Collections.emptyList();
        }

        return multipartFiles.stream()
                .map(multipartFile -> uploadImage(multipartFile, post)) // Post 객체를 uploadImage 메소드로 전달
                .collect(Collectors.toList());
    }

    private Image uploadImage(MultipartFile multipartFile, Post post) {
        String storedImagePath = uploadImageToS3(multipartFile);
        String originName = multipartFile.getOriginalFilename();

        // S3에 업로드된 이미지 URL을 가진 Image 엔티티 객체 생성 및 저장
        Image image = new Image();
        image.setOriginName(originName);
        image.setStoredImagePath(storedImagePath);
        image.setPost(post); // 여기서 Post 객체를 설정
        return imageRepository.save(image);
    }

    // s3에 이미지 업로드
    private String uploadImageToS3(MultipartFile image) {
        // 원본 파일 이름
        String originName = image.getOriginalFilename();
        // 파일 확장자
        String ext = originName.substring(originName.lastIndexOf("."));
        // 중복 방지를 위한 랜덤 파일 이름 생성
        String changedName = UUID.randomUUID().toString() + ext;

        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        try {
            // S3에 파일 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, changedName, image.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }

        // 업로드된 파일 URL 반환
        return amazonS3.getUrl(bucketName, changedName).toString();
    }

    // 이미지 삭제 -> 게시판 수정, 삭제 기능에 필요해서 추가함
    public void deleteImage(Image image) {
        // S3에서 이미지 파일 삭제
        amazonS3.deleteObject(bucketName, extractFileName(image.getStoredImagePath()));

        // 데이터베이스에서 Image 엔티티 삭제
        imageRepository.delete(image);
    }

    private String extractFileName(String fileUrl) {
        // S3 URL에서 파일 이름 추출
        // URL에서 마지막 '/' 이후의 문자열을 파일 이름으로 사용
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

}
