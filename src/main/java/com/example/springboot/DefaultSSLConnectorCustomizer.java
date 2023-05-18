package com.example.springboot;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

public class DefaultSSLConnectorCustomizer implements TomcatConnectorCustomizer {

    private AbstractHttp11Protocol protocol;

    @Override
    public void customize(Connector connector) {
        AbstractHttp11Protocol protocol = (AbstractHttp11Protocol) connector.getProtocolHandler();

        connector.setProperty("bindOnInit", "false");
        if (connector.getSecure()) {
            //--- REMEMBER PROTOCOL WHICH WE NEED LATER IN ORDER TO RELOAD SSL CONFIG
            this.protocol = protocol;
        }
    }

    protected AbstractHttp11Protocol getProtocol() {
        return protocol;
    }
}