package com.example.communityapplication.dto;

import com.example.communityapplication.entity.Posts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PostUpdateResponseDto {
    @Size(min=1)
    @Size(max=26)
    private String title;
    private String content;
    private String file;
    @NotNull
    private Date updatedAt;
    public PostUpdateResponseDto(Posts posts){
        this.updatedAt = posts.getUpdatedAt();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.file = posts.getFile();
    }
}
