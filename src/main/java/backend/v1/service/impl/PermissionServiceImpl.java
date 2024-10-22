package backend.v1.service.impl;

import backend.v1.mapper.PermissionMapper;
import backend.v1.model.Permission;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends MppServiceImpl<PermissionMapper,Permission> {
}
