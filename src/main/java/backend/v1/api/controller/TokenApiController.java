package backend.v1.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TokenApiController {

    @RequestMapping("/token")
    public String getToken() {
        return "123456789";
    }

}
