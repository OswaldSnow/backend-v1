package backend.v1.tools;

import backend.v1.common.JsonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JsonResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonResponse(HttpServletResponse response, Object data) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(data));
        } catch (IOException e) {
            // 记录日志而不是抛出异常
            // 或使用日志框架记录
            log.error("",e);
            // 这里可以选择返回一个默认的错误响应
            try {
                response.getWriter().write(objectMapper.writeValueAsString(JsonResponse.error("内部错误", 500)));
            } catch (IOException ioException) {
                log.error("",ioException);
            }
        }
    }
}
