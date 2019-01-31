/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.soap.webservices.course.management.soap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.Servlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 * @author Chux
 */

//enable spring web services
@EnableWs
//configure spring
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context){
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }  
           
    //course-details.xsd
    @Bean
    public XsdSchema courseSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
    }
    
    // /ws/course.wsdl
    @Bean(name = "courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema courseSchema)
    {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        //PortType - coursePort
        definition.setPortTypeName("coursePort");
        //Namespace - http://stealth.com/courses
        definition.setTargetNamespace("http://stealth.com/courses");
        definition.setLocationUri("/ws");
        definition.setSchema(courseSchema);
        return definition;
    }
    
    //securing the soap service using XwsSecurityInterceptor from spring security
    @Bean
    public XwsSecurityInterceptor securityInterceptor(){
        XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
        //callback handler -> SimplePasswordValidationCallbackHandler
        xwsSecurityInterceptor.setCallbackHandler(callbackHandler());
        //security policy -> securityPolicy.xml
        xwsSecurityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return xwsSecurityInterceptor;
    }
    
    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors)
    {
        interceptors.add(securityInterceptor());
    }
    
    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler(){
        SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
        simplePasswordValidationCallbackHandler.setUsersMap(Collections.singletonMap("User", "Password"));
        return simplePasswordValidationCallbackHandler;
    }
}
