package bo.com.micrium.modulobase.security.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    private Long userId;
    private String userName;
    private String rolName;
    private Long tenantId;

    public static UserContext createWithName(String name) {
        return new UserContext(null, name, null, null);
    }
}
