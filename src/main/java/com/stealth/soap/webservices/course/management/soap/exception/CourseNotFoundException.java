/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.soap.webservices.course.management.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 *
 * @author Chux
 */
@SoapFault(faultCode = FaultCode.CLIENT)//to say this is a client fault because they are passing wrong course code
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String string) {
        super(string);
    }
    
}
