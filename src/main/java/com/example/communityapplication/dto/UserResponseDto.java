package com.example.communityapplication.dto;


import com.example.communityapplication.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserResponseDto {
    @NotBlank
    private Long userId;
    @NotBlank
    @Email
    private String email;
    @Size(min = 1)
    @Size(max = 10)
    @NotBlank
    private String nickname;
    @NotBlank
    private String profilePicture;

    public UserResponseDto(Users user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profilePicture = user.getProfilePicture();
    }
}