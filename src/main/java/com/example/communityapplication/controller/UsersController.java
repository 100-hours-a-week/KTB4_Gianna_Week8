package com.example.communityapplication.controller;

import com.example.communityapplication.dto.*;
import com.example.communityapplication.response.ApiResponse;
import com.example.communityapplication.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UsersController {
    private final UsersService usersService;

    @PostMapping("/signup")
    public ApiResponse<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto userResponseDto;
        try {
            userResponseDto = usersService.create(request.getEmail(), request.getPassword(),request.getNickname(),request.getProfilePicture());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ApiResponse.of("signup_success", userResponseDto);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> userLogin(@Valid @RequestBody LoginRequestDto request) throws IllegalAccessException {
        LoginResponseDto userResponse;
        try {
            userResponse = usersService.userLogin(request.getEmail(), request.getPassword());
        } catch (IllegalAccessException e) {
            throw new IllegalAccessException(e.getMessage());
        }
        return ApiResponse.of("login_sucess", userResponse);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable Long userId){
        UserResponseDto userResponse =  usersService.getUser(userId);
        return ApiResponse.of("get_data_success", userResponse);
    }

    @GetMapping("/{userId}/profilePicture")
    public ApiResponse<ProfilePictureResponseDto> getProfilePicture (@PathVariable Long userId){
        ProfilePictureResponseDto profilePictureResponse = usersService.getUserProfilePicture(userId);
        return ApiResponse.of("get_profile_picture_success", profilePictureResponse);
    }

    @PatchMapping("/{userId}/nickname")
    public ApiResponse<EmptyResponseDto> updateNickname(@PathVariable Long userId, @Valid  @RequestBody UserUpdateRequestDto request){
        usersService.updateNickname(userId, request.getNickname());
        return ApiResponse.of("patch_success", new EmptyResponseDto());
    }

    @PatchMapping("/{userId}/password")
    public ApiResponse<EmptyResponseDto> updatePassword(@PathVariable Long userId, @Valid  @RequestBody UserUpdateRequestDto request){
        usersService.updatePassword(userId, request.getPassword());
        return ApiResponse.of("patch_success", new EmptyResponseDto());
    }

    @PatchMapping("/{userId}/profilePicture")
    public ApiResponse<EmptyResponseDto> updateProfilePicture(@PathVariable Long userId, @Valid  @RequestBody UserUpdateRequestDto request){
        usersService.updateProfilePicture(userId, request.getProfilePicture());
        return ApiResponse.of("patch_success", new EmptyResponseDto());
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<EmptyResponseDto> deleteUser(@PathVariable Long userId){
        usersService.deleteUser(userId);
        return ApiResponse.of("delete_success", new EmptyResponseDto());
    }
}