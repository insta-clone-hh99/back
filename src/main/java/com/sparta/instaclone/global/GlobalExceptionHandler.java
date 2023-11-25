package com.sparta.instaclone.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 게시글에 이미지 업로드 예외처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleImageUploadException(RuntimeException e) {
        // 적절한 HTTP 상태 코드와 에러 메시지 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

