package id.my.agungdh.rakawikchat.repository;

import id.my.agungdh.rakawikchat.document.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByConversationIdOrderByTimestampAsc(String conversationId);

    Message findTopByConversationIdOrderByTimestampDesc(String conversationId);
}
