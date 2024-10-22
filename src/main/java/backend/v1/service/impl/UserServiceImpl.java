package backend.v1.service.impl;

import backend.v1.common.JsonResponse;
import backend.v1.common.PaginationInfo;
import backend.v1.mapper.RolePermissionMapper;
import backend.v1.mapper.UserMapper;
import backend.v1.mapper.UserRoleMapper;
import backend.v1.model.Permission;
import backend.v1.model.Role;
import backend.v1.model.User;
import backend.v1.model.UserRole;
import backend.v1.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends MppServiceImpl<UserMapper,User> implements UserService {

    private UserRoleMapper userRoleMapper;

    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public JsonResponse<List<User>> getAllUsers() {
        Page<User> userPage = new Page<>(1, 2);
        IPage<User> userIPage = this.baseMapper.selectPage(userPage, null);

        PaginationInfo pagination = new PaginationInfo(
                userIPage.getCurrent(),
                userIPage.getPages()    ,
                userIPage.getTotal(),
                userIPage.getSize()
        );

        return JsonResponse.successList(userIPage.getRecords(),pagination);
    }

    @Override
    public List<User> customGetUserList() {
        return this.baseMapper.customGetUserList();
    }

    @Override
    public List<Role> customGetRoleList(Integer id) {
        return this.baseMapper.customGetRoleList(id);
    }

    @Override
    public List<Permission> customGetPermissionList(Integer id) {
        return this.baseMapper.customGetPermissionList(id);
    }

    @Override
    public void addUser(User user, UserRole userRole) {

    }
}
