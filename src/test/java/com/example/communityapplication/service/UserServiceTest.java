package com.example.communityapplication.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock //가짜 객체 생성
    private UsersRepository usersRepository;
    @InjectMocks //가짜 객체 주입
    private UsersService usersService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("User Service Test - updateNickname 비즈니스 로직 단위 테스트")
    void updateNicknameTest_Success(){
        //given
        Users user = new Users("test@test.com", passwordEncoder.encode("Test!234"), "testuser","image.jpg");
        String newNickname = "newName";
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        usersService.updateNickname( 1L , newNickname);

        //then
        assertEquals(newNickname, user.getNickname());
    }

    @Test
    @DisplayName("User Service Test - updatePassword 비즈니스 로직 단위 테스트")
    void updatePasswordTest_Success(){
        //given
        Users user = new Users("test@test.com", passwordEncoder.encode("Test!234"), "testuser","image.jpg");
        String expectedPassword = "TEst!234";
        String encodedPassword = passwordEncoder.encode(expectedPassword);
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        usersService.updatePassword( 1L , encodedPassword);

        //then
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    @DisplayName("User Service Test - updateProfilePicture 비즈니스 로직 단위 테스트")
    void updatePasswordPicture_Success(){
        //given
        Users user = new Users("test@test.com", passwordEncoder.encode("Test!234"), "testuser","image.jpg");
        String newProfilePicture = "new_image.jpg";
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        usersService.updateProfilePicture( 1L , newProfilePicture);

        //then
        assertEquals(newProfilePicture, user.getProfilePicture());
    }
}
