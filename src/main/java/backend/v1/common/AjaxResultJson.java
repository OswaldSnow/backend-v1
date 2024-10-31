package backend.v1.common;

import lombok.Getter;
import java.util.LinkedHashMap;

/**
 * 统一的 API 响应结果封装类
 * @author yourname
 */
public class AjaxResultJson extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    // 使用不可变的常量类来存储键名
    public static final class ResultFields {
        public static final String CODE = "code";
        public static final String MESSAGE = "message";
        public static final String DATA = "data";
        public static final String PAGE_INFO = "pageInfo";

        private ResultFields() {} // 防止实例化
    }

    @Getter
    public enum ResponseCode {
        SUCCESS(2000, "成功"),
        WARN(3001, "警告"),
        ERROR(5000, "错误"),
        UNAUTHORIZED(4001, "未授权"),
        FORBIDDEN(4003, "禁止访问"),
        NOT_FOUND(4004, "资源不存在"),
        INTERNAL_ERROR(5001, "服务器内部错误");

        private final int code;
        private final String message;

        ResponseCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    private AjaxResultJson() {
        super(4); // 指定初始容量
    }

    public static AjaxResultJsonBuilder builder() {
        return new AjaxResultJsonBuilder();
    }

    // Builder 模式构建响应
    public static class AjaxResultJsonBuilder {
        private final AjaxResultJson result;

        private AjaxResultJsonBuilder() {
            result = new AjaxResultJson();
        }

        public AjaxResultJsonBuilder code(ResponseCode responseCode) {
            result.put(ResultFields.CODE, responseCode.getCode());
            return this;
        }

        public AjaxResultJsonBuilder message(String message) {
            result.put(ResultFields.MESSAGE, message);
            return this;
        }

        public AjaxResultJsonBuilder data(Object data) {
            if (data != null) {
                result.put(ResultFields.DATA, data);
            }
            return this;
        }

        public AjaxResultJsonBuilder pageInfo(PaginationInfo pageInfo) {
            if (pageInfo != null) {
                result.put(ResultFields.PAGE_INFO, pageInfo);
            }
            return this;
        }

        public AjaxResultJson build() {
            return result;
        }
    }

    // 快捷方法
    public static AjaxResultJson success() {
        return builder()
                .code(ResponseCode.SUCCESS)
                .message(ResponseCode.SUCCESS.getMessage())
                .build();
    }

    public static AjaxResultJson success(Object data) {
        return builder()
                .code(ResponseCode.SUCCESS)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static AjaxResultJson success(Object data, PaginationInfo pageInfo) {
        return builder()
                .code(ResponseCode.SUCCESS)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .pageInfo(pageInfo)
                .build();
    }

    public static AjaxResultJson error(String message) {
        return builder()
                .code(ResponseCode.ERROR)
                .message(message)
                .build();
    }

    public static AjaxResultJson error(ResponseCode responseCode, String message) {
        return builder()
                .code(responseCode)
                .message(message)
                .build();
    }

    public static AjaxResultJson warn(String message) {
        return builder()
                .code(ResponseCode.WARN)
                .message(message)
                .build();
    }

    @Override
    public AjaxResultJson put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
