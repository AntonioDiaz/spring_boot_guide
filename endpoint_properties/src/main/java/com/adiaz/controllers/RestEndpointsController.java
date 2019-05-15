package com.adiaz.controllers;

import com.adiaz.CourseConfiguration;
import com.adiaz.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestEndpointsController {

    @Value("${default.course.name}")
    private String nameDefault;

    @Value("${default.course.chapterCount}")
    private Integer chapterCountDefault;

    @GetMapping("/")
    public String hello(){
        return "toma toma!!";
    }

    @Autowired
    private CourseConfiguration courseConfiguration;

    @GetMapping("/config")
    public Map<String, Object> config(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", courseConfiguration.getName());
        map.put("chapterCount", courseConfiguration.getChapterCount());
        map.put("author", courseConfiguration.getAuthor());
        map.put("rating", courseConfiguration.getRating());
        return map;
    }

    @GetMapping("/course")
    public Course getEndpoint(
            @RequestParam(defaultValue = "Spring Boot", required = false) String name,
            @RequestParam(defaultValue = "2", required = false) Integer chapterCount) {
        return new Course(name, chapterCount);
    }

    @GetMapping("/default")
    public Course getDefaultCourse(){
        return new Course(nameDefault, chapterCountDefault);
    }

    @PostMapping("/register/course")
    public String saveCourse(@RequestBody Course course) {
        return String.format("Your course named %s with %s chapters saved successfully", course.getName(), course.getChapterCount());
    }
}
