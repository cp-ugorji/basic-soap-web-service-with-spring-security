/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.soap.webservices.course.management.soap.service;

import com.stealth.soap.webservices.course.management.soap.bean.Course;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chux
 */
@Component
public class CourseDetailsService {
    
    public enum Status {
        SUCCESS, FAILURE
    }
    
    private static List<Course> courses = new ArrayList<>();
    static{
        Course c1 = new Course(1, "Spring", "Spring Basics");
        courses.add(c1);
        
        Course c2 = new Course(2, "Spring MVC", "Spring MVC Basics");
        courses.add(c2);
        
        Course c3 = new Course(3, "Spring JPA", "Spring JPA Basics");
        courses.add(c3);
        
        Course c4 = new Course(4, "Spring Security", "Spring Security Basics");
        courses.add(c4);
    }
    
    //course - 1
    public Course findById(int id)
    {
        for(Course course : courses)
        {
            if(course.getId() == id)
                return course;
        }
        return null;
    }
    
    public List<Course> getAllCourses()
    {
        return courses;
    }
    
    public Status/*int*/ deleteById(int id)
    {
        Iterator<Course> iterator = courses.iterator();
        while(iterator.hasNext())
        {
            Course course = iterator.next();
            if(course.getId() == id)
            {
                iterator.remove();
                return Status.SUCCESS;//1;//success
            }
        }
        return Status.FAILURE;//0;//failure
    }
}
