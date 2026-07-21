package id.my.agungdh.rakawikchat.service;

import id.my.agungdh.rakawikchat.document.Conversation;
import id.my.agungdh.rakawikchat.document.Message;
import id.my.agungdh.rakawikchat.dto.request.CreateConversationRequest;
import id.my.agungdh.rakawikchat.dto.request.SendMessageRequest;
import id.my.agungdh.rakawikchat.dto.response.ConversationResponse;
import id.my.agungdh.rakawikchat.dto.response.MessageResponse;
import id.my.agungdh.rakawikchat.mapper.EntityMapper;
import id.my.agungdh.rakawikchat.repository.ConversationRepository;
import id.my.agungdh.rakawikchat.repository.MessageRepository;
import id.my.agungdh.rakawikchat.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final EntityMapper entityMapper;

    public List<ConversationResponse> getConversations(UserContext userContext) {
        return conversationRepository
                .findByParticipantsContainingOrderByUpdatedAtDesc(userContext.getUsername())
                .stream()
                .map(conv -> {
                    Message lastMsg = messageRepository
                            .findTopByConversationIdOrderByTimestampDesc(conv.getId());
                    ConversationResponse resp = entityMapper.toConversationResponse(conv);
                    if (lastMsg != null) {
                        resp.setLastMessage(entityMapper.toMessageResponse(lastMsg));
                    }
                    return resp;
                })
                .collect(Collectors.toList());
    }

    public ConversationResponse createConversation(UserContext userContext,
                                                    CreateConversationRequest request) {
        List<String> participants = new ArrayList<>(request.getParticipants());
        if (!participants.contains(userContext.getUsername())) {
            participants.add(userContext.getUsername());
        }

        Conversation conversation = Conversation.builder()
                .participants(participants)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        conversation = conversationRepository.save(conversation);

        return entityMapper.toConversationResponse(conversation);
    }

    public List<MessageResponse> getMessages(String conversationId) {
        return messageRepository
                .findByConversationIdOrderByTimestampAsc(conversationId)
                .stream()
                .map(entityMapper::toMessageResponse)
                .collect(Collectors.toList());
    }

    public MessageResponse sendMessage(UserContext userContext, SendMessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversation.getParticipants().contains(userContext.getUsername())) {
            throw new RuntimeException("Not a participant of this conversation");
        }

        Message message = Message.builder()
                .conversationId(request.getConversationId())
                .senderUsername(userContext.getUsername())
                .content(request.getContent())
                .contentType(Message.ContentType.valueOf(request.getContentType()))
                .timestamp(Instant.now())
                .readBy(new ArrayList<>())
                .build();
        message = messageRepository.save(message);

        conversation.setUpdatedAt(message.getTimestamp());
        conversationRepository.save(conversation);

        return entityMapper.toMessageResponse(message);
    }

    public void markAsRead(String conversationId, UserContext userContext) {
        List<Message> messages = messageRepository
                .findByConversationIdOrderByTimestampAsc(conversationId);
        for (Message msg : messages) {
            if (!msg.getReadBy().contains(userContext.getUsername())) {
                msg.getReadBy().add(userContext.getUsername());
                messageRepository.save(msg);
            }
        }
    }
}
