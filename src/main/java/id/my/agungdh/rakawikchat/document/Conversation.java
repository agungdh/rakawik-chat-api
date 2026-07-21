package id.my.agungdh.rakawikchat.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    private String id;

    private List<String> participants;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
