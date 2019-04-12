package com.elearningapp.tech5soft.upskill.Search;

public class Courses {

    private String courseName;
    private int coursePhoto;

    public Courses(String courseName, int coursePhoto) {
        this.courseName = courseName;
        this.coursePhoto = coursePhoto;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCoursePhoto() {
        return coursePhoto;
    }

    public void setCoursePhoto(int coursePhoto) {
        this.coursePhoto = coursePhoto;
    }
}
