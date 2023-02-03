package com.paw.tgt.auth;

import com.paw.tgt.config.JwtService;
import com.paw.tgt.user.entity.Role;
import com.paw.tgt.user.entity.User;
import com.paw.tgt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email((request.getEmail()))
                .addr(request.getAddr())
                .phone(request.getPhone())
                .type(request.getType())
                .regDate(request.getRegDate())
                .memModDate(request.getMemModDate())
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        
    }
}
