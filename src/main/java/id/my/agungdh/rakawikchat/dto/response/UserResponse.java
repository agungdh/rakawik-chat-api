package id.my.agungdh.rakawikchat.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String fullName;

    @Builder.Default
    private Boolean online = false;
}
