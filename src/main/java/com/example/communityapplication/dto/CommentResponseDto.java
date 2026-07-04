package com.example.communityapplication.dto;

import com.example.communityapplication.entity.Comments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    @NotBlank
    private Long commentId;
    @Size(min=1)
    @Size(max=10)
    @NotBlank
    private String author;
    @NotBlank
    private String content;
    @NotBlank
    private Date createdAt;

    public CommentResponseDto(Comments comments){
        this.commentId = comments.getId();
        this.author = comments.getAuthor();
        this.content = comments.getContent();
        this.createdAt = comments.getCreatedAt();
    }
}
