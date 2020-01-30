package com.ey.telefonica.rpa.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }


    @Bean(name = "k2")
    public DefaultWsdl11Definition defaultWsdl11DefinitionForK2(XsdSchema k2Schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("K2Port");
        wsdl11Definition.setLocationUri("/k2");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(k2Schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema k2Schema() {
        return new SimpleXsdSchema(new ClassPathResource("k2.xsd"));
    }

    @Bean(name = "ivr")
    public DefaultWsdl11Definition defaultWsdl11DefinitionForIvr(XsdSchema NscrSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("NscrPort");
        wsdl11Definition.setLocationUri("/nscr");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(NscrSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema NscrSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Nscr.xsd"));
    }
}