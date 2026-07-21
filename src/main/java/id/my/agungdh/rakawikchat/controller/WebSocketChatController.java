package id.my.agungdh.rakawikchat.controller;

import id.my.agungdh.rakawikchat.dto.request.SendMessageRequest;
import id.my.agungdh.rakawikchat.dto.response.MessageResponse;
import id.my.agungdh.rakawikchat.security.UserContext;
import id.my.agungdh.rakawikchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload SendMessageRequest request,
                            SimpMessageHeaderAccessor headerAccessor) {
        UserContext userContext = (UserContext) headerAccessor.getSessionAttributes().get("user");
        MessageResponse response = chatService.sendMessage(userContext, request);
        messagingTemplate.convertAndSend(
                "/topic/conversation/" + request.getConversationId(),
                response
        );
    }
}
