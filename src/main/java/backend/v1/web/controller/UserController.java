package backend.v1.web.controller;

import backend.v1.common.JsonResponse;
import backend.v1.model.*;
import backend.v1.service.UserService;
import backend.v1.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web/user")
@Slf4j
public class UserController {

    private static final String PageList = "user/userList";

    private static final String PageAdd = "user/userAdd";

    private static final String PageEdit = "user/userEdit";

    private static final String PageDetail = "user/userDetail";

    private UserService userService;

    private RoleServiceImpl roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String UserList(ModelMap map) {
        log.info("/user/list is visited");
        List<User> users = userService.customGetUserList();
        map.put("users", users);

        List<Role> userRoles = userService.customGetRoleList(1);
        List<Permission> userPermissions = userService.customGetPermissionList(1);

        return PageList;
    }

    @GetMapping("/add")
    public String UserAdd() {
        return PageAdd;
    }

    @PostMapping("/add")
    @ResponseBody
    public JsonResponse<User> UserAdd(User user) {

        return JsonResponse.success(user);
    }

    @GetMapping("/detail")
    public String UserDetail() {
        return PageDetail;
    }

    @GetMapping("/edit")
    public String UserEdit() {
        return PageEdit;
    }

}
