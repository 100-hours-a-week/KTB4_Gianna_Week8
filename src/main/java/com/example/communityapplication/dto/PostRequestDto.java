package com.example.communityapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    @Size(min=1)
    @Size(max=26)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String file;
}