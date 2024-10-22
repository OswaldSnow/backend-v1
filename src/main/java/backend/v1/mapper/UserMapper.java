package backend.v1.mapper;

import backend.v1.model.Permission;
import backend.v1.model.Role;
import backend.v1.model.User;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

import java.util.List;

public interface UserMapper extends MppBaseMapper<User> {

    List<User> customGetUserList();

    List<Role> customGetRoleList(Integer id);

    List<Permission> customGetPermissionList(Integer id);
}
