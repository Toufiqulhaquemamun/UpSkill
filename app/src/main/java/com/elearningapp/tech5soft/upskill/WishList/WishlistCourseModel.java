package com.elearningapp.tech5soft.upskill.WishList;

public class WishlistCourseModel {

    private String courseCode;

    private String courseName;

    private Object eRROR;

    private Integer empID;

    private Integer orgID;

    private Integer pKID;

    private Integer wishFlag;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Object getERROR() {
        return eRROR;
    }

    public void setERROR(Object eRROR) {
        this.eRROR = eRROR;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public Integer getPKID() {
        return pKID;
    }

    public void setPKID(Integer pKID) {
        this.pKID = pKID;
    }

    public Integer getWishFlag() {
        return wishFlag;
    }

    public void setWishFlag(Integer wishFlag) {
        this.wishFlag = wishFlag;
    }

}
