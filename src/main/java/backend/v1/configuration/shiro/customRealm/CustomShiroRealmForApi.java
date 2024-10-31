package backend.v1.configuration.shiro.customRealm;

import backend.v1.configuration.shiro.customToken.ApiAuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
public class CustomShiroRealmForApi extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        // 只支持 ApiAuthenticationToken
        return token instanceof ApiAuthenticationToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 从 principalCollection 中获取认证阶段存储的 principal
        String tokenString = (String) principalCollection.getPrimaryPrincipal();

        // 解析 token，获取用户角色和权限信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // authorizationInfo.addRole("admin");
        // authorizationInfo.addStringPermission("user:view");

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ApiAuthenticationToken apiToken = (ApiAuthenticationToken) authenticationToken;
        String tokenString = (String) apiToken.getCredentials();

//        throw new AuthenticationException("测试认证token失败");
        // 验证 token
        // 可以是 JWT 验证、API key 验证等
//        if (!validateToken(tokenString)) {
//            throw new AuthenticationException("Invalid token");
//        }

        // 返回认证信息
        return new SimpleAuthenticationInfo(
                tokenString, // principal (后续可以通过 SecurityUtils.getSubject().getPrincipal() 获取)
                tokenString, // credentials
                getName()    // realmName
        );
    }
}
