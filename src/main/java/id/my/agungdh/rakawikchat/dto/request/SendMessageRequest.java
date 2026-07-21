package id.my.agungdh.rakawikchat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendMessageRequest {

    @NotBlank
    private String conversationId;

    @NotBlank
    private String content;

    private String contentType = "TEXT";
}
