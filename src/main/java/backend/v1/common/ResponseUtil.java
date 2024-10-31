package backend.v1.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象以JSON格式写入响应
     * @param response HttpServletResponse
     * @param result AjaxResultJson对象
     */
    public static void writeJson(HttpServletResponse response, AjaxResultJson result) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            String jsonString = objectMapper.writeValueAsString(result);
            response.getWriter().write(jsonString);
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("Response write json error", e);
            // 尝试发送一个简单的错误响应
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"code\":500,\"message\":\"System error\"}");
            } catch (IOException ex) {
                // 如果连简单响应都失败了，只能记录日志
                log.error("Failed to send error response", ex);
            }
        }
    }

}
