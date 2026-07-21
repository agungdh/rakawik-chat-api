package id.my.agungdh.rakawikchat.repository;

import id.my.agungdh.rakawikchat.document.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

    List<Conversation> findByParticipantsContainingOrderByUpdatedAtDesc(String username);
}
