package com.adiaz.endpointyaml;

import lombok.Data;

@Data
public class Course {
    private String name;
    private Integer chapterCount;

    public Course(String name, Integer chapterCount) {
        this.name = name;
        this.chapterCount = chapterCount;
    }
}
