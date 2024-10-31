package backend.v1.web.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.configuration.exceptionHandle.CustomException;
import backend.v1.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
@Slf4j
public class AppIndexController {

    private final String PageIndex = "index";

    @RequestMapping("/index")
    @RequiresPermissions("user:list")
    public String index(ModelMap map){
        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
//        SecurityUtils.getSubject().getPrincipals().forEach(System.out::println);
        map.put("currentUser", currentUser);
        return PageIndex;
    }

    @RequestMapping("/")
    public String rootPath(){
        return "redirect:/app/index";
    }

    @RequestMapping("/makeException")
    public void makeException() {
        throw new CustomException("测试自定义错误");
    }

    @RequestMapping("/testAjaxResult")
    @ResponseBody
    public AjaxResultJson testAjaxResult(){

        return AjaxResultJson.success();
    }


}
