package id.my.agungdh.rakawikchat.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ConversationResponse {

    private String id;
    private List<String> participants;
    private Instant createdAt;
    private Instant updatedAt;
    private MessageResponse lastMessage;
}
