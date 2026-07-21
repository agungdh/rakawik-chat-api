package id.my.agungdh.rakawikchat.service;

import id.my.agungdh.rakawikchat.dto.request.LoginRequest;
import id.my.agungdh.rakawikchat.dto.response.LoginResponse;
import id.my.agungdh.rakawikchat.entity.User;
import id.my.agungdh.rakawikchat.mapper.EntityMapper;
import id.my.agungdh.rakawikchat.repository.UserRepository;
import id.my.agungdh.rakawikchat.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EntityMapper entityMapper;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = tokenService.generateToken(user);
        tokenService.refreshToken(token);

        return LoginResponse.builder()
                .token(token)
                .user(entityMapper.toUserResponse(user))
                .build();
    }

    public void logout(String token) {
        tokenService.invalidateToken(token);
    }
}
