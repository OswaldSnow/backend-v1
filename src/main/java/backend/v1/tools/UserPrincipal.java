package backend.v1.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal {
    private Integer id;
    private String username;
    private List<String> roles;
    // 其他需要的用户信息
}
