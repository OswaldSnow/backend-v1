package backend.v1.web.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.model.*;
import backend.v1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public String UserList(ModelMap map) {
        List<User> users = userService.customGetUserList();
        map.put("users", users);
        return PageList;
    }

    @GetMapping("/add")
    @RequiresPermissions("user:add")
    public String UserAdd() {
        return PageAdd;
    }

    @PostMapping("/add")
    @ResponseBody
    @RequiresPermissions("user:add")
    public AjaxResultJson UserAdd(User user) {

        return AjaxResultJson.success(user);
    }

    @GetMapping("/detail")
    @RequiresPermissions("user:detail")
    public String UserDetail() {
        return PageDetail;
    }

    @GetMapping("/edit")
    @RequiresPermissions("user:edit")
    public String UserEdit() {
        return PageEdit;
    }

}
