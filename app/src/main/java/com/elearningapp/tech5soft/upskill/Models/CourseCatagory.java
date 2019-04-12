package com.elearningapp.tech5soft.upskill.Models;

import java.util.ArrayList;

public class CourseCatagory {

     Integer categoryID;
     String categoryName;
     Object eRROR;
     ArrayList<CourseList> courseList;


    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Object geteRROR() {
        return eRROR;
    }

    public void seteRROR(Object eRROR) {
        this.eRROR = eRROR;
    }

    public ArrayList<CourseList> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<CourseList> courseList) {
        this.courseList = courseList;
    }
}
