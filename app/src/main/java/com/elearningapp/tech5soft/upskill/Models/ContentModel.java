package com.elearningapp.tech5soft.upskill.Models;

public class ContentModel {

    String LectureTitle;
    String CourseCode;
    String TopicCode;
    String LectureID;
    String Overview;
    String curriCulum;
    String uri;

    public String getLectureTitle() {
        return LectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        LectureTitle = lectureTitle;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getTopicCode() {
        return TopicCode;
    }

    public void setTopicCode(String topicCode) {
        TopicCode = topicCode;
    }

    public String getLectureID() {
        return LectureID;
    }

    public void setLectureID(String lectureID) {
        LectureID = lectureID;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getCurriCulum() {
        return curriCulum;
    }

    public void setCurriCulum(String curriCulum) {
        this.curriCulum = curriCulum;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
