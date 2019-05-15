package com.adiaz;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("course")
@Data
public class CourseConfiguration {
    private String name;
    private Integer chapterCount;
    private Integer rating;
    private String author;
}
