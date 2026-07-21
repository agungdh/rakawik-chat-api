package id.my.agungdh.rakawikchat.service;

import id.my.agungdh.rakawikchat.dto.response.UserResponse;
import id.my.agungdh.rakawikchat.entity.User;
import id.my.agungdh.rakawikchat.mapper.EntityMapper;
import id.my.agungdh.rakawikchat.repository.UserRepository;
import id.my.agungdh.rakawikchat.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final PresenceService presenceService;

    public UserResponse getCurrentUser(UserContext userContext) {
        User user = userRepository.findById(userContext.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse resp = entityMapper.toUserResponse(user);
        resp.setOnline(presenceService.isOnline(user.getUsername()));
        return resp;
    }

    public List<UserResponse> getAllUsers() {
        Set<String> onlineUsers = presenceService.getOnlineUsernames();
        return userRepository.findAll().stream()
                .map(entityMapper::toUserResponse)
                .peek(u -> u.setOnline(onlineUsers.contains(u.getUsername())))
                .collect(Collectors.toList());
    }
}
