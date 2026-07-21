package id.my.agungdh.rakawikchat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateConversationRequest {

    @NotEmpty
    private List<String> participants;
}
