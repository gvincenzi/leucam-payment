package org.leucam.payment.configuration;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class LeucamBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    public static String REALM="LEUCAM_REST_API";

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(REALM);
        super.afterPropertiesSet();
    }
}
