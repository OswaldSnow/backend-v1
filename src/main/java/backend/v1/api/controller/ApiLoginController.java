package backend.v1.api.controller;

import backend.v1.common.AjaxResultJson;
import backend.v1.model.User;
import backend.v1.service.UserService;
import backend.v1.tools.JwtTools;
import backend.v1.tools.RsaTools;
import backend.v1.tools.UserPrincipal;
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
    public String publicKey() {
        return rsaTools.getPublicKeyString();
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
        // 解密密码
        byte[] decryptedBytes = rsaTools.decrypt(
                Base64.getDecoder().decode(user.getPassword())
        );
        String password = new String(decryptedBytes, StandardCharsets.UTF_8);

        // 验证用户名和密码

        // 创建用户主体
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getLoginAccount(),
                Collections.EMPTY_LIST
        );

        // 生成token
        String token = jwtTools.generateToken(userPrincipal);
        String refreshToken = jwtTools.generateRefreshToken(userPrincipal);

        return AjaxResultJson.success().put("token",token).put("refreshToken",refreshToken);
    }

}
