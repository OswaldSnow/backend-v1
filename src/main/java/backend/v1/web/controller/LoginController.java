package backend.v1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/web")
public class LoginController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request){

        return "index";
    }

}
