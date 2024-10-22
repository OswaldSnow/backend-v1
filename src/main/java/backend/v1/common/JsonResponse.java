package backend.v1.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse<T> {
    private String status;      // 响应状态
    private String message;     // 响应信息
    private T data;             // 泛型数据
    private int code;           // 状态码
    private PaginationInfo paginationInfo;  // 分页信息

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>("success", "请求成功", data, 200,null);
    }

    public static JsonResponse<Void> error(String message, int code) {
        return new JsonResponse<>("error", message, null, code,null);
    }

    public static JsonResponse<Void> warning(String message) {
        return new JsonResponse<>("warning", message, null, 300,null);
    }

    // 新增方法：处理集合数据的响应
    public static <T> JsonResponse<List<T>> successList(List<T> data, PaginationInfo paginationInfo) {
        return new JsonResponse<>("success", "请求成功", data, 200,paginationInfo);
    }

}
