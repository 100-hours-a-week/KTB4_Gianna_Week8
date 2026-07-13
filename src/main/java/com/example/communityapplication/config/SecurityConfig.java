package com.example.communityapplication.config;

import com.example.communityapplication.entity.Users;
import com.example.communityapplication.repository.UsersRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터 체인을 활성화
@EnableMethodSecurity //메소드 수준 보안을 활성화. 메소드에서 preAuthorize를 진행하기 위하
public class SecurityConfig {
    private final UsersRepository usersRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        //접근 허용 제한 - URL 기반 보안
        http
                .csrf(csrf->csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/users/login", "/users/signup", "/login-process").permitAll() // 모두에게 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                );
        http.formLogin(form -> form
                .loginProcessingUrl("/login-process")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(this.loginSuccessHandler())
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출처 설정
        configuration.setAllowedOrigins(List.of("http://localhost:5500", "http://127.0.0.1:5500"));

        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 HTTP 헤더 설정
        configuration.setAllowedHeaders(List.of("*"));

        // 자격 증명(쿠키, 인증 헤더 등)을 허용할지 여부
        configuration.setAllowCredentials(true);

        // 예비 요청(Preflight) 결과 캐시 시간 설정
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위에서 정의한 CORS 정책 적용
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public SecurityConfig(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            String email = authentication.getName();

            Users user = usersRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("user not found"));

            Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
            userIdCookie.setPath("/");
            userIdCookie.setMaxAge(60 * 60); // 1시간
            userIdCookie.setHttpOnly(false); // FE에서 document.cookie로 읽어야 하면 false

            response.addCookie(userIdCookie);

            response.sendRedirect("http://localhost:5500/board/board.html");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
        return new BCryptPasswordEncoder();
    }
}