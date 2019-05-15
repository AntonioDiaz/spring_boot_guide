package com.adiaz.endpointyaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestEndPoint {

    @Autowired
    CourseConfiguration courseConfiguration;

    @Value("${default.course.name}")
    private String nameDefault;

    @Value("${default.course.chapterCount}")
    private Integer chapterCountDefault;

    @GetMapping("/default")
    public Course nameDefault () {
        return new Course(nameDefault, chapterCountDefault);
    }

    @GetMapping("/config")
    public Map<String, String> config () {
        Map<String, String> map = new HashMap<>();
        map.put("name", courseConfiguration.getName());
        map.put("author", courseConfiguration.getAuthor());
        return map;
    }

}
