package com.example.communityapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfilePictureResponseDto {
    @NotBlank
    private String profilePicture;
    public ProfilePictureResponseDto(String profilePicture){
        this.profilePicture = profilePicture;
    }
}
