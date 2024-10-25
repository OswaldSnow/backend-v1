package backend.v1.tools;

import javax.servlet.http.HttpServletRequest;

public class Tools {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith) ||
                (request.getHeader("Accept") != null &&
                        request.getHeader("Accept").contains("application/json"));
    }
}
