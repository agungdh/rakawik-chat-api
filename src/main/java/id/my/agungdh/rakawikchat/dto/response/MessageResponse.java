package id.my.agungdh.rakawikchat.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class MessageResponse {

    private String id;
    private String conversationId;
    private String senderUsername;
    private String content;
    private String contentType;
    private Instant timestamp;
    private List<String> readBy;
}
