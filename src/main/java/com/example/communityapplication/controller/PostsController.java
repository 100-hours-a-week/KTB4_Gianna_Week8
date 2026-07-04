package com.example.communityapplication.controller;

import com.example.communityapplication.dto.*;
import com.example.communityapplication.response.ApiResponse;
import com.example.communityapplication.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@CrossOrigin
public class PostsController {
    private final PostService postService;

    @PostMapping("/{userId}")
    public ApiResponse<PostResponseDto> createPost(@PathVariable Long userId, @Valid @RequestBody PostRequestDto request) {
        PostResponseDto postResponse = postService.createPost(userId, request.getDate(),request.getTitle(),request.getContent(),request.getFile());
        return ApiResponse.of("post_success", postResponse);
    }

    //전체 조회
    @GetMapping
    public ApiResponse<PostsListResponseDto> getPostList() {
     PostsListResponseDto postLististResponse = postService.getPostList();
        return ApiResponse.of("get_success", postLististResponse);
    }

    //상세 조회
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponse = postService.getPost(postId);
        return ApiResponse.of("get_success", postResponse);
    }

    //게시글 수정
    @PatchMapping("/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(@PathVariable Long postId, @Valid  @RequestBody PostUpdateRequestDto request) {
        PostUpdateResponseDto postResponse = postService.updatePost(postId, request.getTitle(),request.getContent(),request.getFile());
        return ApiResponse.of("patch_success", postResponse);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<EmptyResponseDto> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.of("delete_success", new EmptyResponseDto());
    }
}
