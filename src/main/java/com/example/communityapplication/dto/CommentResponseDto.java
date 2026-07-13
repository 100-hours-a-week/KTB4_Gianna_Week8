package com.example.communityapplication.dto;

import com.example.communityapplication.entity.Comments;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String author;
    private String content;
    private Date createdAt;

    public CommentResponseDto(Comments comments){
        this.commentId = comments.getId();
        this.author = comments.getAuthor();
        this.content = comments.getContent();
        this.createdAt = comments.getCreatedAt();
    }
}
