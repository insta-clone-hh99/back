package com.sparta.instaclone.global.jwt;

public enum ErrorCode {
    UNEXPECTED_ERROR("An unexpected error occurred.", 500),
    INVALID_TOKEN("잘못된 토큰입니다.", 403);

    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
