package backend.v1.web.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/app")
public class LoginController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public AjaxResultJson login(HttpServletRequest request, User user) {
//        if( user.getLoginAccount().equals("zhangsan") ){
//            throw new CustomException("ajax异常测试");
//        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginAccount(), user.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        return AjaxResultJson.success(user);
    }

}
