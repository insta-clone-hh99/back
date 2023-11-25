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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageS3Service{

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름

    public List<Image> uploadImages(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(this::uploadImage)
                .collect(Collectors.toList());
    }

    private Image uploadImage(MultipartFile multipartFile) {
        String storedImagePath = uploadImageToS3(multipartFile);
        String originName = multipartFile.getOriginalFilename();

        // 아직 Post 객체와 연결되지 않은 Image 객체를 생성
        return Image.builder()
                .originName(originName)
                .storedImagePath(storedImagePath)
                .build();
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
}
