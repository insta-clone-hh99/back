package com.sparta.instaclone.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentListResponseDto {
    private List<CommentResponseDto> data;
}
