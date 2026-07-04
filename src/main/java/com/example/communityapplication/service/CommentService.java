package com.example.communityapplication.service;


import com.example.communityapplication.dto.CommentResponseDto;
import com.example.communityapplication.dto.CommentsListResponseDto;
import com.example.communityapplication.entity.Comments;
import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.CommentsRepository;
import com.example.communityapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Date;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;

    public CommentResponseDto createComment(Long postId, Long userId, String content, Date createdAt){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));
        Comments comment = new Comments(
                postId,
                userId,
                user.getNickname(),
               content,
                createdAt
        );
        commentsRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @GetMapping
    public CommentsListResponseDto getComment(Long postId){
        return new CommentsListResponseDto(commentsRepository.findByPostId(postId));
    }

    @PatchMapping("/{commentId}")
    public CommentsListResponseDto patchComment(Long postId, Long commentId, String newContent){
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));

        comment.update(newContent);
        commentsRepository.save(comment);

        return getComment(postId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(Long commentId){
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));
        commentsRepository.delete(comment);
    }
}
