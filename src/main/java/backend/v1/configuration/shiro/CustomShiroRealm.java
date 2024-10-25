package backend.v1.configuration.shiro;

import backend.v1.model.Permission;
import backend.v1.model.Role;
import backend.v1.model.User;
import backend.v1.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomShiroRealm extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 用户权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");

        if (currentUser != null){

            Set<Role> roleSet = userService.customGetRoleList(currentUser.getId());
            Set<String> roleStringSet = roleSet
                    .stream()
                    .map( obj -> obj.getRoleName() )
                    .collect(Collectors.toSet());
            info.addRoles(roleStringSet);

            Set<Permission> permissionSet = userService.customGetPermissionList(currentUser.getId());
            Set<String> permissionStringSet = permissionSet
                    .stream()
                    .map( obj -> obj.getPermissionCode() )
                    .collect(Collectors.toSet());
            info.addStringPermissions(permissionStringSet);

        }

        return info;
    }

    // 用户验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String loginAccount = (String)upToken.getPrincipal();
        String password = new String(upToken.getPassword());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account", loginAccount);
        User dataUser = userService.getOne(queryWrapper);
        if (dataUser == null) {
            throw new UnknownAccountException();
        } else if (!dataUser.getPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }

        SecurityUtils.getSubject().getSession().setAttribute("currentUser", dataUser);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginAccount, password, getName());
        return info;
    }
}
