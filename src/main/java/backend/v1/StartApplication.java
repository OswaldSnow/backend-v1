package backend.v1;

import backend.v1.common.JsonResponse;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("backend.v1.mapper")
@EnableMPP
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartApplication.class);
        app.run(StartApplication.class, args);
    }

}
