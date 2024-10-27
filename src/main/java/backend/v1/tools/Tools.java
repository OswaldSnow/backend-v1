package backend.v1.tools;

import javax.servlet.http.HttpServletRequest;

public class Tools {

    /**
     * 判读是否是 ajax 请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        // 检查常见的 AJAX 标志头
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }

        // 检查请求头 Accept
        String accept = request.getHeader("Accept");
        if (accept != null && (
                accept.contains("application/json") ||
                        accept.contains("application/javascript") ||
                        accept.contains("text/javascript"))) {
            return true;
        }

        // 检查请求头 Content-Type
        String contentType = request.getContentType();
        if (contentType != null && (
                contentType.contains("application/json") ||
                        contentType.contains("application/javascript"))) {
            return true;
        }

        // 检查请求 URL 或参数中的标识
//        String requestURI = request.getRequestURI();
//        if (requestURI != null && (
//                requestURI.endsWith(".json") ||
//                        requestURI.contains("/api/") ||
//                        request.getParameter("ajax") != null)) {
//            return true;
//        }

        return false;
    }
}
