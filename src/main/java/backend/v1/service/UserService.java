package backend.v1.service;

import backend.v1.common.JsonResponse;
import backend.v1.model.Permission;
import backend.v1.model.Role;
import backend.v1.model.User;
import backend.v1.model.UserRole;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;

public interface UserService extends IMppService<User> {

    JsonResponse<List<User>> getAllUsers();

    List<User> customGetUserList();

    List<Role> customGetRoleList(Integer id);

    List<Permission> customGetPermissionList(Integer id);

    void addUser(User user, UserRole userRole);
}
