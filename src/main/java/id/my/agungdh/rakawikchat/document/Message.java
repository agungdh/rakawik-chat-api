package id.my.agungdh.rakawikchat.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    private String id;

    private String conversationId;

    private String senderUsername;

    private String content;

    @Builder.Default
    private ContentType contentType = ContentType.TEXT;

    @Builder.Default
    private Instant timestamp = Instant.now();

    @Builder.Default
    private List<String> readBy = new ArrayList<>();

    public enum ContentType {
        TEXT, IMAGE, FILE
    }
}
