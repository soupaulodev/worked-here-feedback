package br.com.soupaulodev.springwithsoap.config;

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
public class SoapWebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "user")
    public DefaultWsdl11Definition userWsdl(XsdSchema userSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("UserPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("https://soupaulodev.com.br/user");
        wsdl.setSchema(userSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/user.xsd"));
    }

    @Bean(name = "feedback")
    public DefaultWsdl11Definition feedbackWsdl(XsdSchema feedbackSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("FeedbackPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("https://soupaulodev.com.br/feedback");
        wsdl.setSchema(feedbackSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema feedbackSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/feedback.xsd"));
    }
}
