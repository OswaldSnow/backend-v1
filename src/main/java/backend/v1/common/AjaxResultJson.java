package backend.v1.common;

import java.util.LinkedHashMap;

/**
 * 统一 ajax 返回 json 格式
 */
public class AjaxResultJson extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "message";

    public static final String DATA_TAG = "data";

    public static final String PAGE_TAG = "pageInfo";

    public AjaxResultJson() {}

    public AjaxResultJson(int code, String msg) {
        this.put(CODE_TAG, code);
        this.put(MSG_TAG, msg);
    }

    public AjaxResultJson(int code, String msg, Object data) {
        this.put(CODE_TAG, code);
        this.put(MSG_TAG, msg);
        if (data != null) {
            this.put(DATA_TAG, data);
        }
    }

    public AjaxResultJson(int code, String msg,Object data, PaginationInfo pageInfo) {
        this.put(CODE_TAG, code);
        this.put(MSG_TAG, msg);
        if (data != null) {
            this.put(DATA_TAG, data);
        }
        if (pageInfo != null) {
            this.put(PAGE_TAG, pageInfo);
        }
    }

    @Override
    public AjaxResultJson put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public enum ResponseCode {
        SUCCESS(2000),
        WORN(3001),
        ERROR(5000)
        ;

        ResponseCode(int code){
            this.code = code;
        }

        private final int code;

        public int getCode() {
            return code;
        }
    }

    public enum ResponseMessage{
        SUCCESS_MSG("成功"),
        WORN_MSG("警告"),
        ERROR_MSG("错误")
        ;

        private final String message;

        ResponseMessage(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }

    /**
     * 成功相关
     * @param msg
     * @param data
     * @param pageInfo
     * @return
     */
    public static AjaxResultJson success(String msg, Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), msg, data, pageInfo);
    }

    public static AjaxResultJson success(String msg, Object data) {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static AjaxResultJson success(Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), ResponseMessage.SUCCESS_MSG.getMessage(), data, pageInfo);
    }

    public static AjaxResultJson success(Object data) {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), ResponseMessage.SUCCESS_MSG.getMessage(), data);
    }

    public static AjaxResultJson success(String msg) {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static AjaxResultJson success() {
        return new AjaxResultJson(ResponseCode.SUCCESS.getCode(), ResponseMessage.SUCCESS_MSG.getMessage());
    }

    /**
     * 错误相关
     * @param msg
     * @param data
     * @param pageInfo
     * @return
     */
    public static AjaxResultJson error(String msg, Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), msg, data, pageInfo);
    }

    public static AjaxResultJson error(String msg, Object data) {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), msg, data);
    }

    public static AjaxResultJson error(Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), ResponseMessage.ERROR_MSG.getMessage(), data, pageInfo);
    }

    public static AjaxResultJson error(Object data) {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), ResponseMessage.ERROR_MSG.getMessage(), data);
    }

    public static AjaxResultJson error(String msg) {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), msg);
    }

    public static AjaxResultJson error() {
        return new AjaxResultJson(ResponseCode.ERROR.getCode(), ResponseMessage.ERROR_MSG.getMessage());
    }

    /**
     * 警告相关
     * @param msg
     * @param data
     * @param pageInfo
     * @return
     */
    public static AjaxResultJson worn(String msg, Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), msg, data, pageInfo);
    }

    public static AjaxResultJson worn(String msg, Object data) {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), msg, data);
    }

    public static AjaxResultJson worn(Object data, PaginationInfo pageInfo) {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), ResponseMessage.WORN_MSG.getMessage(), data, pageInfo);
    }

    public static AjaxResultJson worn(Object data) {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), ResponseMessage.WORN_MSG.getMessage(), data);
    }

    public static AjaxResultJson worn(String msg) {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), msg);
    }

    public static AjaxResultJson worn() {
        return new AjaxResultJson(ResponseCode.WORN.getCode(), ResponseMessage.WORN_MSG.getMessage());
    }

}
