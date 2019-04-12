package com.elearningapp.tech5soft.upskill.Models;

public class CourseDetailsModel {

    private String CourseName;
    private String CourseCode;
    private String LearningObjective;
    private String TitleCertificate;

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getLearningObjective() {
        return LearningObjective;
    }

    public void setLearningObjective(String learningObjective) {
        LearningObjective = learningObjective;
    }

    public String getTitleCertificate() {
        return TitleCertificate;
    }

    public void setTitleCertificate(String titleCertificate) {
        TitleCertificate = titleCertificate;
    }
}
