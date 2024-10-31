package backend.v1.configuration.shiro.customShiroFilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承 shiro 的默认 authc 过滤器
 * 此处只是为了处理访问 / 路径的行为
 * 其他行为使用默认实现即可
 */
@Slf4j
public class CoverFormAuthenticationFilterForApp extends FormAuthenticationFilter {

    private static final String REQUEST_ROOT_PATH_REDIRECT_PATH = "/app/index";

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 检查用户是否已认证
        if (subject.isAuthenticated() && "/".equals(httpRequest.getRequestURI())) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(REQUEST_ROOT_PATH_REDIRECT_PATH); // 302 重定向
            return false; // 返回 false 表示请求未被处理
        }

        // 继续执行其他权限检查
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if("/".equals(httpRequest.getRequestURI())) {
            return false;
        }else{
            return super.isAccessAllowed(request, response, mappedValue);
        }

    }
}
