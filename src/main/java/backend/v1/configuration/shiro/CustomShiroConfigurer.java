package backend.v1.configuration.shiro;

import backend.v1.configuration.shiro.customRealm.CustomShiroRealmForApi;
import backend.v1.configuration.shiro.customRealm.CustomShiroRealmForApp;
import backend.v1.configuration.shiro.customRealm.PathMatchingRealmAuthenticator;
import backend.v1.configuration.shiro.customShiroFilter.ApiAuthenticationFilter;
import backend.v1.configuration.shiro.customShiroFilter.CoverFormAuthenticationFilterForApp;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
@Slf4j
public class CustomShiroConfigurer {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomShiroRealmForApp customShiroRealmForApp, CustomShiroRealmForApi customShiroRealmForApi) {
        // 创建安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 创建自定义的 ModularRealmAuthenticator
        PathMatchingRealmAuthenticator authenticator = new PathMatchingRealmAuthenticator();

        // 配置路径与 Realm 的映射关系
        Map<String, Collection<Realm>> pathRealms = new HashMap<>();
        pathRealms.put("/app/**", Collections.singletonList(customShiroRealmForApp));
        pathRealms.put("/api/**", Collections.singletonList(customShiroRealmForApi));
        authenticator.setPathRealms(pathRealms);

        // 设置认证策略（可选） 当前策略：一个 Realm 认证成功即可
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());

        // 设置 Authenticator
        securityManager.setAuthenticator(authenticator);

        // 设置所有可用的 Realm
        List<Realm> realms = Arrays.asList(customShiroRealmForApp, customShiroRealmForApi);
        securityManager.setRealms(realms);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String,String> chainMap = new LinkedHashMap<>();
        chainMap.put("/logout", "logout");

        chainMap.put("/favicon.ico","anon");
        chainMap.put("/static/**", "anon");
        chainMap.put("/app/login", "anon");
        chainMap.put("/api/login", "anon");

        // app/** 和 api/** 分别使用不同的过滤器
        chainMap.put("/app/**", "auth");
        chainMap.put("/api/**", "apiAuth");

        // 默认 to be continued
        chainMap.put("/**", "auth");

//        log.info("=== Shiro Filter Chains ===");
//        chainMap.forEach((k, v) -> log.info("Path: {}, Filter: {}", k, v));

        filterFactoryBean.setFilterChainDefinitionMap(chainMap);

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("auth", new CoverFormAuthenticationFilterForApp());
        filters.put("apiAuth", new ApiAuthenticationFilter());
        filterFactoryBean.setFilters(filters);

        filterFactoryBean.setLoginUrl("/app/login");

        return filterFactoryBean;
    }

}
