package com.co.kr.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerConfig {
	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		Connector ajpConnector = new Connector("AJP/1.3");
		ajpConnector.setPort(ajpPort);
		ajpConnector.setAllowTrace(false);
		//ajpConnector.setSecure(false);
		//ajpConnector.setScheme("http");
		((AbstractAjpProtocol<?>)ajpConnector.getProtocolHandler()).setSecretRequired(false);
		
		tomcat.addAdditionalTomcatConnectors(ajpConnector);
		return tomcat;
	}
}