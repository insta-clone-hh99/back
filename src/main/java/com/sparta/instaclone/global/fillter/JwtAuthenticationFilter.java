package com.sparta.instaclone.global.fillter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.instaclone.domain.user.dto.LoginRequestDto;
import com.sparta.instaclone.global.jwt.JwtUtil;
import com.sparta.instaclone.global.secuity.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String email = userDetails.getUsername();
        String role = "ROLE_USER";
        String userName = userDetails.getUserName();
        String nickname = userDetails.getNickname();

        String accessToken = jwtUtil.createAccessToken(email, role);

        // JWT를 헤더에 추가
        jwtUtil.addJwtToHeader(JwtUtil.ACCESSTOKEN_HEADER, accessToken, response);

        // CORS 헤더를 설정
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 추가할 헤더 설정
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Another-Header, Yet-Another-Header, Other-Custom-Header, Content-Encoding, Kuma-Revision");

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("email", email);
        tokens.put("userName", userName);
        tokens.put("nickname", nickname);
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"ID 또는 비밀번호가 틀립니다\"}");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
