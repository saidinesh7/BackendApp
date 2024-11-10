package com.saloonapp.app.config.identity;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CSPFilter> cspFilter() {
        FilterRegistrationBean<CSPFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CSPFilter());
        registrationBean.addUrlPatterns("/api/*", "/swagger-ui/*", "/v3/api-docs/**"); // Adjust URL patterns
        return registrationBean;
    }
}

