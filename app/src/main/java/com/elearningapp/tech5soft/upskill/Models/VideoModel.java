package com.elearningapp.tech5soft.upskill.Models;

public class VideoModel {

    private String actStatus;
    private Integer categoryID;
    private Object contentImg;
    private Integer contentTyp;
    private String contentTypNM;
    private String courseCode;
    private Object courseCurriculum;
    private String courseDateTime;
    private String courseNM;
    private Object eRROR;
    private Integer empID;
    private String fileExtension;
    private String fileName;
    private Integer fileSize;
    private Integer lectureID;
    private String lectureNM;
    private String lectureTitle;
    private String overView;
    private Integer pKID;
    private String recStatus;
    private String topicCode;
    private String topicNM;
    private Object videoFile;
    private Integer completeflag;


    String url;
    String duration;
    String position;

    public VideoModel(String url) {
        this.url=url;
    }

    public VideoModel() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Object getContentImg() {
        return contentImg;
    }

    public void setContentImg(Object contentImg) {
        this.contentImg = contentImg;
    }

    public Integer getContentTyp() {
        return contentTyp;
    }

    public void setContentTyp(Integer contentTyp) {
        this.contentTyp = contentTyp;
    }

    public String getContentTypNM() {
        return contentTypNM;
    }

    public void setContentTypNM(String contentTypNM) {
        this.contentTypNM = contentTypNM;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Object getCourseCurriculum() {
        return courseCurriculum;
    }

    public void setCourseCurriculum(Object courseCurriculum) {
        this.courseCurriculum = courseCurriculum;
    }

    public String getCourseDateTime() {
        return courseDateTime;
    }

    public void setCourseDateTime(String courseDateTime) {
        this.courseDateTime = courseDateTime;
    }

    public String getCourseNM() {
        return courseNM;
    }

    public void setCourseNM(String courseNM) {
        this.courseNM = courseNM;
    }

    public Object geteRROR() {
        return eRROR;
    }

    public void seteRROR(Object eRROR) {
        this.eRROR = eRROR;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getLectureID() {
        return lectureID;
    }

    public void setLectureID(Integer lectureID) {
        this.lectureID = lectureID;
    }

    public String getLectureNM() {
        return lectureNM;
    }

    public void setLectureNM(String lectureNM) {
        this.lectureNM = lectureNM;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Integer getpKID() {
        return pKID;
    }

    public void setpKID(Integer pKID) {
        this.pKID = pKID;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicNM() {
        return topicNM;
    }

    public void setTopicNM(String topicNM) {
        this.topicNM = topicNM;
    }

    public Object getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(Object videoFile) {
        this.videoFile = videoFile;
    }

    public Integer getCompleteflag() {
        return completeflag;
    }

    public void setCompleteflag(Integer completeflag) {
        this.completeflag = completeflag;
    }
}
