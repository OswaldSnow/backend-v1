package backend.v1.api.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.model.User;
import backend.v1.service.UserService;
import backend.v1.tools.JwtTools;
import backend.v1.tools.RsaTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ApiLoginController {

    private UserService userService;

    private RsaTools rsaTools;

    private JwtTools jwtTools;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRsaTools(RsaTools rsaTools) {
        this.rsaTools = rsaTools;
    }

    @Autowired
    public void setJwtTools(JwtTools jwtTools) {
        this.jwtTools = jwtTools;
    }

    @RequestMapping("/publicKey")
    public AjaxResultJson publicKey() {

        return AjaxResultJson.success("public key content");
    }


    /**
     * 存在 bug 2024-11-02
     * to be continued
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public AjaxResultJson login(@RequestBody User user) throws Exception {

        return AjaxResultJson.success(user);
    }

}
