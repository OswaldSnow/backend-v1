package backend.v1.mapper;

import backend.v1.model.Permission;
import backend.v1.model.Role;
import backend.v1.model.User;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

import java.util.List;
import java.util.Set;

public interface UserMapper extends MppBaseMapper<User> {

    List<User> customGetUserList();

    Set<Role> customGetRoleList(Integer id);

    Set<Permission> customGetPermissionList(Integer id);
}
