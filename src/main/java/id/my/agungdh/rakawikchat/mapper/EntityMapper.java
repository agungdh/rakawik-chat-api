package id.my.agungdh.rakawikchat.mapper;

import id.my.agungdh.rakawikchat.document.Conversation;
import id.my.agungdh.rakawikchat.document.Message;
import id.my.agungdh.rakawikchat.dto.response.ConversationResponse;
import id.my.agungdh.rakawikchat.dto.response.MessageResponse;
import id.my.agungdh.rakawikchat.dto.response.UserResponse;
import id.my.agungdh.rakawikchat.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "contentType", expression = "java(message.getContentType().name())")
    MessageResponse toMessageResponse(Message message);

    @Mapping(target = "lastMessage", ignore = true)
    ConversationResponse toConversationResponse(Conversation conversation);
}
