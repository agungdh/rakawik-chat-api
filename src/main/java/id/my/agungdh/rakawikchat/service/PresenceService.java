package id.my.agungdh.rakawikchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "user:online:";

    public void setOnline(String username) {
        redisTemplate.opsForValue().set(PREFIX + username, "1");
    }

    public void setOffline(String username) {
        redisTemplate.delete(PREFIX + username);
    }

    public boolean isOnline(String username) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + username));
    }

    public Set<String> getOnlineUsernames() {
        return redisTemplate.keys(PREFIX + "*").stream()
                .map(key -> key.substring(PREFIX.length()))
                .collect(Collectors.toSet());
    }
}
