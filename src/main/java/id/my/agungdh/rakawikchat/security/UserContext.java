package id.my.agungdh.rakawikchat.security;

import id.my.agungdh.rakawikchat.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    private Long id;
    private String username;
    private String fullName;

    public static UserContext from(User user) {
        return new UserContext(user.getId(), user.getUsername(), user.getFullName());
    }
}
