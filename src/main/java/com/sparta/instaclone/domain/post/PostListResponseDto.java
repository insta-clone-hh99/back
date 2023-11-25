package com.sparta.instaclone.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostListResponseDto {
    private List<PostResponseDto> data;
}
