package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.errors.LoginFailException;
import com.codesoom.assignment.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(SessionCommand.SessionRequest command) {
        final User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new LoginFailException(command.getEmail()));

        if (!user.authenticate(command.getPassword())) {
            throw new LoginFailException(command.getEmail());
        }

        return jwtUtil.encode(user.getId());
    }

    public Long parseToken(String accessToken) {
        final Claims claims = jwtUtil.decode(accessToken);
        return claims.get("userId", Long.class);
    }
}