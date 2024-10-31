package backend.v1.api.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiLoginController {

    @RequestMapping("/login")
    public AjaxResultJson getToken() {
        String token = "模拟token";
        return AjaxResultJson.success().put("token", token);
    }

}
