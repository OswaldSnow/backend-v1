package backend.v1.configuration.shiro.customToken;

import org.apache.shiro.authc.AuthenticationToken;

public class ApiAuthenticationToken implements AuthenticationToken {

    private String token;

    public ApiAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
