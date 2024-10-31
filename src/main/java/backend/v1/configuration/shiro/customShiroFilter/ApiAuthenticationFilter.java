package backend.v1.configuration.shiro.customShiroFilter;

import backend.v1.common.AjaxResultJson;
import backend.v1.common.ResponseUtil;
import backend.v1.configuration.exceptionHandle.CustomException;
import backend.v1.configuration.shiro.customToken.ApiAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiAuthenticationFilter extends AuthenticatingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 对于无状态API请求，让它每次都进入认证流程
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        try {
            // 关键在这里：必须调用 executeLogin 才会触发 createToken
            return executeLogin(request, response);
        } catch (Exception e) {
            ResponseUtil.writeJson((HttpServletResponse) response,
                    AjaxResultJson.error(e.getMessage()));
            return false;
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 从请求头或参数中获取 token
        String token = httpRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException("token is empty");
        }
        return new ApiAuthenticationToken(token); // 自定义的 Token 对象
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {

        ResponseUtil.writeJson((HttpServletResponse) response, AjaxResultJson.error(e.getMessage()));
        return false;
    }
}
