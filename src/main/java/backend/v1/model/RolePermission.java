package backend.v1.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_permission")
public class RolePermission {
    @MppMultiId("role_id")
    private Integer roleId;
    @MppMultiId("permission_id")
    private Integer permissionId;
}
