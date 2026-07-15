package com.example.communityapplication.service;

import org.junit.jupiter.api.BeforeEach;
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

    private Users user;

    @BeforeEach
    void setUp(){
        user = new Users("test@test.com", "oldPassword", "testuser","image.jpg");
        //updateNickname 함수 내부에 보면 repository 조회를 하는 로직이 있다. 그 때의 반환값을 설정해주는 역할
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("User Service Test - updateNickname 비즈니스 로직 단위 테스트")
    void updateNicknameTest_Success(){
        //given
        String newNickname = "newName";

        //when
        usersService.updateNickname( 1L , newNickname);

        //then
        assertEquals(newNickname, user.getNickname());
    }

    @Test
    @DisplayName("User Service Test - updatePassword 비즈니스 로직 단위 테스트")
    void updatePasswordTest_Success(){
        //given
        String newPassword = "newPassword";
        String encodedPassword = "eNcOdEdPaSsWoRd";
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        //when
        usersService.updatePassword( 1L , newPassword);
        //then
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    @DisplayName("User Service Test - updateProfilePicture 비즈니스 로직 단위 테스트")
    void updateProfilePicture_Success(){
        //given
        String newProfilePicture = "new_image.jpg";

        //when
        usersService.updateProfilePicture( 1L , newProfilePicture);

        //then
        assertEquals(newProfilePicture, user.getProfilePicture());
    }
}
