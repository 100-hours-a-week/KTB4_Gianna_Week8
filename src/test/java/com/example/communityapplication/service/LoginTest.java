package com.example.communityapplication.service;

import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc //컨트롤러 계층에서 자동으로 MockMVC 주입 -> 실행 없이 응답 모킹 가능
@Transactional
public class LoginTest {

    @Autowired //자동으로 의존관계 주입
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Users user = new Users(
                "test@test.com",
                passwordEncoder.encode("Test234!"),
                "테스트유저",
                "image.jpg"
        );

        usersRepository.save(user);
    }

    @Test
    void LoginTest_Success() throws Exception {
        // mockMvc.perform : 가상의 HTTP 요청을 컨트롤러로 전송 -> 동작 검증
        mockMvc.perform(post("/login-process")
                //contentType : 요청 본문의 데이터 형식을 서버에 알림 -> 체이닝 형식으로 엮음
                        //MediaType : HTTP 요청/응답 시 주고 받는 데이터의 형식
                        // APPLICATION_FORM_URLENCODED ; application/x-www-form-urlencoded 요청을 테스트하기 위함 ( formLogin 형식)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@test.com") //체이닝 방식으로 본문 구성
                        .param("password", ("Test234!")))
                //authenticated() : 인증 확인
                .andExpect(authenticated()
                        .withUsername("test@test.com"));
    }

    @Test
    void LoginTest_Password_Fail() throws Exception {
        mockMvc.perform(post("/login-process")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@test.com")
                        .param("password", "test1234"))
                .andExpect(unauthenticated());
    }
}
