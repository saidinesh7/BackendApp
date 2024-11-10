package com.saloonapp.app.config.identity;


import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@Component
@WebFilter("/*") // Apply to all URLs
public class CSPFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

                HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Setting the Content-Security-Policy header for the response
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.setHeader("Content-Security-Policy",
            "default-src 'self'; "+
            "connect-src 'self' http://localhost:8080; " +  // Allow connections to API
            "script-src 'self' 'unsafe-inline'; " +  // Allow inline scripts (adjust as needed)
            "style-src 'self' 'unsafe-inline'; " +  // Allow inline styles (adjust as needed)
            "img-src 'self' data:; " +  // Allow images from the same origin and data URIs
            "font-src 'self'; " + // Allow fonts from the same origin
            "frame-src 'self'; "  // Allow frames from the same origin
        );
        chain.doFilter(request, response); // Continue with the request
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}
