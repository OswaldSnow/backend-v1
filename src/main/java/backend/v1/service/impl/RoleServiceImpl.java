package backend.v1.service.impl;

import backend.v1.mapper.RoleMapper;
import backend.v1.model.Role;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends MppServiceImpl<RoleMapper, Role> {

}
