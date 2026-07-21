package id.my.agungdh.rakawikchat.service;

import id.my.agungdh.rakawikchat.dto.response.UserResponse;
import id.my.agungdh.rakawikchat.entity.User;
import id.my.agungdh.rakawikchat.mapper.EntityMapper;
import id.my.agungdh.rakawikchat.repository.UserRepository;
import id.my.agungdh.rakawikchat.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public UserResponse getCurrentUser(UserContext userContext) {
        User user = userRepository.findById(userContext.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return entityMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(entityMapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
