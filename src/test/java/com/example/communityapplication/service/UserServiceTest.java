package com.example.communityapplication.service;

import com.example.communityapplication.dto.LoginResponseDto;
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

    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersService usersService;

    @Test
    @DisplayName("User Service Test - updateNickname 로직 단위 테스트")
    void updateNicknameTest_Success(){
        //given
        Users user = new Users("test@test.com", "Test!234", "testuser","image.jpg");
        String expectedName = "newName";
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        usersService.updateNickname( 1L , expectedName);

        //then
        assertEquals(expectedName, user.getNickname());
    }


}
