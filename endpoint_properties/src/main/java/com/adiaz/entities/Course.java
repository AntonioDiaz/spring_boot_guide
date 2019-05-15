package com.adiaz.entities;

import lombok.Data;

@Data
public class Course {
    private String name;
    private Integer chapterCount;
    private String author;
    private Integer rating;

    public Course(String name, Integer chapterCount) {
        this.name = name;
        this.chapterCount = chapterCount;
    }
}
