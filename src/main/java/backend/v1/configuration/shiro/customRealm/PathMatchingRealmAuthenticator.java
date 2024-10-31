package backend.v1.configuration.shiro.customRealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PathMatchingRealmAuthenticator extends ModularRealmAuthenticator {

    private Map<String, Collection<Realm>> pathRealms = new HashMap<>();
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken token) throws AuthenticationException {
        String requestPath = getCurrentPath();

        // 遍历所有路径模式，找到匹配的 Realm
        Collection<Realm> matchedRealms = null;
        for (Map.Entry<String, Collection<Realm>> entry : pathRealms.entrySet()) {
            String pattern = entry.getKey();
            if (pathMatcher.match(pattern, requestPath)) {
                matchedRealms = entry.getValue();
                break;
            }
        }

        // 如果没找到匹配的，使用默认的 Realm 集合
        if (matchedRealms == null || matchedRealms.isEmpty()) {
            matchedRealms = getRealms();
        }

        // 执行认证
        if (matchedRealms.size() == 1) {
            return doSingleRealmAuthentication(matchedRealms.iterator().next(), token);
        } else {
            return doMultiRealmAuthentication(matchedRealms, token);
        }
    }

    private String getCurrentPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRequestURI();
        }
        return null;
    }

    public void setPathRealms(Map<String, Collection<Realm>> pathRealms) {
        this.pathRealms = pathRealms;
    }
}
