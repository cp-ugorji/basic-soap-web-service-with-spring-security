/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.soap.webservices.course.management.soap;

import com.stealth.courses.CourseDetails;
import com.stealth.courses.DeleteCourseDetailsRequest;
import com.stealth.courses.DeleteCourseDetailsResponse;
import com.stealth.courses.GetAllCourseDetailsRequest;
import com.stealth.courses.GetAllCourseDetailsResponse;
import com.stealth.courses.GetCourseDetailsRequest;
import com.stealth.courses.GetCourseDetailsResponse;
import com.stealth.soap.webservices.course.management.soap.bean.Course;
import com.stealth.soap.webservices.course.management.soap.exception.CourseNotFoundException;
import com.stealth.soap.webservices.course.management.soap.service.CourseDetailsService;
import com.stealth.soap.webservices.course.management.soap.service.CourseDetailsService.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author Chux
 */

@Endpoint //this tell springboot that this class is an endpoint
public class CourseDetailsEndpoint {
    
    @Autowired
    CourseDetailsService courseDetailsService;
    
    //input -> GetCourseDetailsRequest
    //output -> GetCourseDetailsResponse
    //@PayloadRoot tells springboot that the root of this method is based on an xsd file with namespace as http://stealth.com/courses 
    //and the local operation identified by localPart is GetCourseDetailsRequest
    //@RequestPayload identifies GetCourseDetailsRequest as the request xml
    @PayloadRoot(namespace = "http://stealth.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request)
    {
        //check course exist
        Course course = courseDetailsService.findById(request.getId());
        
        if(course == null)
            throw new CourseNotFoundException("Invalid Course Id: " + request.getId());
        
        return mapCourseDetails(course);
    }
    
    private GetCourseDetailsResponse mapCourseDetails(Course course)
    {
        //if it does, generate a response
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        response.setCourseDetails(mapCourse(course));
        return response;
    }
    
    //GetAllCourseDetailsRequest
    @PayloadRoot(namespace = "http://stealth.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request)
    {
        //check course exist
        List<Course> courses = courseDetailsService.getAllCourses();        
        return mapAllCourseDetails(courses);
    }
    
    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses)
    {
        //if it does, generate a response
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
//        for(Course c :  courses)
//        {
//            CourseDetails mapCourse = mapCourse(c);
//            response.getCourseDetails().add(mapCourse);
//        }
        courses.stream()
                .map((c) -> mapCourse(c))
                .forEachOrdered((mapCourse) -> {
            response.getCourseDetails().add(mapCourse);
        });  
        return response;
    }
    
    //DeleteCourseDetailsRequest
    @PayloadRoot(namespace = "http://stealth.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request)
    {
        //check course exist
        //int status = courseDetailsService.deleteById(request.getId());
        Status status = courseDetailsService.deleteById(request.getId());
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));
        return response;
    }
    
    
    private CourseDetails mapCourse(Course course)
    {
        CourseDetails courseDetail  = new CourseDetails();
        courseDetail.setId(course.getId());
        courseDetail.setName(course.getName());
        courseDetail.setDescription(course.getDescription());
        return courseDetail;
    }
    
    private com.stealth.courses.Status mapStatus(Status status)
    {
        if(status == Status.FAILURE)
            return com.stealth.courses.Status.FAILURE;
        return com.stealth.courses.Status.SUCCESS;
    }
}
