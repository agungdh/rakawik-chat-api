package id.my.agungdh.rakawikchat.config;

import id.my.agungdh.rakawikchat.security.TokenService;
import id.my.agungdh.rakawikchat.security.UserContext;
import id.my.agungdh.rakawikchat.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;
    private final PresenceService presenceService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                UserContext userContext = tokenService.validateToken(token);
                if (userContext != null) {
                    accessor.setUser(new StompPrincipal(userContext.getUsername()));
                    accessor.getSessionAttributes().put("user", userContext);
                    presenceService.setOnline(userContext.getUsername());
                }
            }
        }

        return message;
    }
}
