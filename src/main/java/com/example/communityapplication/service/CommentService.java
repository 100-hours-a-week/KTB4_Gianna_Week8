package com.example.communityapplication.service;


import com.example.communityapplication.dto.CommentResponseDto;
import com.example.communityapplication.dto.CommentsListResponseDto;
import com.example.communityapplication.entity.Comments;
import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.CommentsRepository;
import com.example.communityapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;

    public CommentResponseDto createComment(Long postId, Long userId, String content){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));
        Comments comment = new Comments(
                postId,
                userId,
                user.getNickname(),
                content,
                new Date()
        );
        commentsRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public CommentsListResponseDto getComment(Long postId){
        return new CommentsListResponseDto(commentsRepository.findByPostId(postId));
    }

    public CommentsListResponseDto patchComment(Long postId, Long commentId, String newContent){
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));

        comment.update(newContent);
        commentsRepository.save(comment);

        return getComment(postId);
    }

    public void deleteComment(Long commentId){
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found"));
        commentsRepository.delete(comment);
    }

    public void deleteAllCommentFromPost(Long postId){
        List<Comments> commentsList = commentsRepository.findByPostId(postId);
        for (Comments comment : commentsList) {
            commentsRepository.delete(comment);
        }
    }
}
