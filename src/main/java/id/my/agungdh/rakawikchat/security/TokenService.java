package id.my.agungdh.rakawikchat.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.agungdh.rakawikchat.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOKEN_PREFIX = "auth:token:";
    private static final Duration TOKEN_TTL = Duration.ofDays(30);

    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        String key = TOKEN_PREFIX + token;
        try {
            String userJson = objectMapper.writeValueAsString(UserContext.from(user));
            redisTemplate.opsForValue().set(key, userJson, TOKEN_TTL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store token", e);
        }
        return token;
    }

    public UserContext validateToken(String token) {
        String key = TOKEN_PREFIX + token;
        String userJson = redisTemplate.opsForValue().get(key);
        if (userJson == null) {
            return null;
        }
        try {
            return objectMapper.readValue(userJson, UserContext.class);
        } catch (Exception e) {
            log.warn("Failed to deserialize token data", e);
            return null;
        }
    }

    public void invalidateToken(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.delete(key);
    }

    public void refreshToken(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.expire(key, TOKEN_TTL);
    }
}
