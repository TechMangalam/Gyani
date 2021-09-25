package com.bitaam.gyani.modals;

public class CourseItemModel {

    String authId,author,organisation,title,topic,url,courseId;
    boolean visibility;

    public CourseItemModel() {
    }

    public CourseItemModel(String authId, String author, String organisation, String title, String topic, String url, String courseId, boolean visibility) {
        this.authId = authId;
        this.author = author;
        this.organisation = organisation;
        this.title = title;
        this.topic = topic;
        this.url = url;
        this.courseId = courseId;
        this.visibility = visibility;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
