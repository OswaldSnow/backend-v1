package backend.v1.configuration.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class CustomShiroConfigurer {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomShiroRealm customShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customShiroRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager,CustomShiroFilter customShiroFilter) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("customShiroFilter", customShiroFilter);
        filterFactoryBean.setFilters(filters);

        // Define filter chain maps
        Map<String,String> chainMap = new LinkedHashMap<>();
        chainMap.put("/static/**", "anon");
        chainMap.put("/css/**", "anon");
        chainMap.put("/js/**", "anon");
        chainMap.put("/img/**", "anon");
        chainMap.put("/web/login", "anon");

        chainMap.put("/","customShiroFilter");

        chainMap.put("/logout", "logout");

        chainMap.put("/**", "authc");

        // Define filter chain
        filterFactoryBean.setFilterChainDefinitionMap(chainMap);

        // Redirect to login page if not authenticated
        filterFactoryBean.setLoginUrl("/web/login");
//        filterFactoryBean.setSuccessUrl("/web/index");
//        filterFactoryBean.setUnauthorizedUrl("/web/unauthorized");

        return filterFactoryBean;
    }

}
