package backend.v1.service.impl;

import backend.v1.mapper.RolePermissionMapper;
import backend.v1.model.RolePermission;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;

@Service("rolePermissionService")
public class RolePermissionServiceImpl extends MppServiceImpl<RolePermissionMapper, RolePermission> {
}
