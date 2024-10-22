package backend.v1.service.impl;

import backend.v1.mapper.UserRoleMapper;
import backend.v1.model.UserRole;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;

@Service("userRoleService")
public class UserRoleServiceImpl extends MppServiceImpl<UserRoleMapper, UserRole> {
}
