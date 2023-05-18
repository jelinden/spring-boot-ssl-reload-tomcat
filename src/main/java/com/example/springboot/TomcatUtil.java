package com.example.springboot;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TomcatUtil {

    private ServletWebServerFactory servletWebServerFactory;
    private ServletWebServerFactory servletContainer;

    public TomcatUtil(ServletWebServerFactory servletWebServerFactory, ServletWebServerFactory servletContainer) {
        this.servletWebServerFactory = servletWebServerFactory;
        this.servletContainer = servletContainer;
    }

    public void reloadSSLHostConfig() {
        System.out.println("Reloading SSL host configuration");
        TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) servletWebServerFactory;
        Collection<TomcatConnectorCustomizer> customizers = tomcatFactory.getTomcatConnectorCustomizers();
        for (TomcatConnectorCustomizer tomcatConnectorCustomizer : customizers) {

            if (tomcatConnectorCustomizer instanceof DefaultSSLConnectorCustomizer) {
                DefaultSSLConnectorCustomizer customizer = (DefaultSSLConnectorCustomizer) tomcatConnectorCustomizer;
                AbstractHttp11Protocol protocol = customizer.getProtocol();
                try {
                    protocol.reloadSslHostConfigs();
                    System.out.println("Reloaded SSL host configuration");
                } catch (IllegalArgumentException e) {
                    System.out.println("Cannot reload SSL host configuration" + e.getLocalizedMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
