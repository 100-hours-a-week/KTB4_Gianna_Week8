package com.example.communityapplication.controller;

import com.example.communityapplication.dto.CommentRequestDto;
import com.example.communityapplication.dto.CommentResponseDto;
import com.example.communityapplication.dto.CommentsListResponseDto;
import com.example.communityapplication.response.ApiResponse;
import com.example.communityapplication.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
@CrossOrigin
public class CommentsController {
    private final CommentService commentService;

    @PostMapping("/{userId}")
    public ApiResponse<CommentResponseDto> createComment(@PathVariable Long postId, @PathVariable Long userId, @Valid @RequestBody CommentRequestDto request){
        CommentResponseDto commentResponse = commentService.createComment(postId, userId, request.getContent(), request.getCreatedAt());
        return ApiResponse.of("post_success", commentResponse);
    }

    @GetMapping
    public ApiResponse<CommentsListResponseDto> getComment(@PathVariable Long postId){
        CommentsListResponseDto commentResponse = commentService.getComment(postId);
        return ApiResponse.of("get_success", commentResponse);
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<CommentsListResponseDto> patchComment(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody CommentRequestDto request){
        CommentsListResponseDto commentResponse = commentService.patchComment(postId, commentId, request.getContent());
        return ApiResponse.of("patch_success", commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment( @PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }
}
