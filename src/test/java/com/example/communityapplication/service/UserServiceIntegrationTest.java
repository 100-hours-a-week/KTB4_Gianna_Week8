package com.example.communityapplication.service;

import com.example.communityapplication.dto.LoginResponseDto;
import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {
    @Autowired
    UsersService usersService;
    @Autowired
    UsersRepository usersRepository;

    @Test
    @DisplayName("User Service Test - login 통합 테스트")
    void loginTest_Success() throws IllegalAccessException {
        //given
        Users user = new Users("test@test.com", "Test!234", "testuser","image.jpg");
        usersRepository.save(user);

        //then
        LoginResponseDto loginResponseDto = usersService.userLogin("test@test.com", "Test!234");

        //when
        assertEquals(user.getId(), loginResponseDto.getId());
    }
}
