package id.my.agungdh.rakawikchat.controller;

import id.my.agungdh.rakawikchat.dto.request.CreateConversationRequest;
import id.my.agungdh.rakawikchat.dto.response.ApiResponse;
import id.my.agungdh.rakawikchat.dto.response.ConversationResponse;
import id.my.agungdh.rakawikchat.dto.response.MessageResponse;
import id.my.agungdh.rakawikchat.security.UserContext;
import id.my.agungdh.rakawikchat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/conversations")
    public ResponseEntity<ApiResponse<List<ConversationResponse>>> getConversations(
            @AuthenticationPrincipal UserContext userContext) {
        return ResponseEntity.ok(ApiResponse.<List<ConversationResponse>>builder()
                .success(true)
                .data(chatService.getConversations(userContext))
                .build());
    }

    @PostMapping("/conversations")
    public ResponseEntity<ApiResponse<ConversationResponse>> createConversation(
            @AuthenticationPrincipal UserContext userContext,
            @Valid @RequestBody CreateConversationRequest request) {
        return ResponseEntity.ok(ApiResponse.<ConversationResponse>builder()
                .success(true)
                .data(chatService.createConversation(userContext, request))
                .build());
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getMessages(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<List<MessageResponse>>builder()
                .success(true)
                .data(chatService.getMessages(id))
                .build());
    }

    @PutMapping("/conversations/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable String id,
            @AuthenticationPrincipal UserContext userContext) {
        chatService.markAsRead(id, userContext);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Marked as read")
                .build());
    }
}
