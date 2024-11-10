package com.saloonapp.app.config.swagger;



import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        // Configure HTTP connector to redirect to HTTPS
        factory.addConnectorCustomizers(connector -> {
            connector.setScheme("http");
            connector.setSecure(false);
            connector.setPort(8080); // HTTP port
            connector.setRedirectPort(443); // Redirect to HTTPS port
        });

        return factory;
    }
}

