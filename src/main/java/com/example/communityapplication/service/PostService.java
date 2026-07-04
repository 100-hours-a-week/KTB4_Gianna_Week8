package com.example.communityapplication.service;

import com.example.communityapplication.dto.PostResponseDto;
import com.example.communityapplication.dto.PostUpdateResponseDto;
import com.example.communityapplication.dto.PostsListResponseDto;
import com.example.communityapplication.entity.Posts;
import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.CommentsRepository;
import com.example.communityapplication.repository.PostsRepository;
import com.example.communityapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    private final CommentService commentService;

    public PostResponseDto createPost(Long userId, Date date, String title, String content, String file) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        String author = user.getNickname();
        Posts post = new Posts(
                userId,
                author,
                date,
                title,
                content,
                file
        );
        postsRepository.save(post);
        return new PostResponseDto(post);
    }

    public PostsListResponseDto getPostList() {
        List<Posts> postList = postsRepository.findAll();
        return new PostsListResponseDto(postList);
    }

    public PostResponseDto getPost(Long postId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        return new PostResponseDto(post);
    }

    public PostUpdateResponseDto updatePost(Long postId, String newTitle, String newContent, String newFile) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        post.updatePost(newTitle, newContent,  newFile);
        postsRepository.save(post);
        return new PostUpdateResponseDto(post);
    }


    public void deletePost(Long postId) {
        //댓글 먼저 전부 삭제 후 -> 게시글 삭제
        commentService.deleteAllCommentFromPost(postId);

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found"));
        postsRepository.delete(post);
    }
}
