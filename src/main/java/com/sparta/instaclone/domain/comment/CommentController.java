package com.sparta.instaclone.domain.comment;

import com.sparta.instaclone.domain.comment.dto.CommentListResponseDto;
import com.sparta.instaclone.domain.comment.dto.CommentRequestDto;
import com.sparta.instaclone.domain.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
                                                            @RequestParam Long userId,
                                                            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto commentResponse = commentService.createComment(postId, userId, requestDto);
        return ResponseEntity.ok(commentResponse);
    }

    // 댓글 목록 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }


    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
