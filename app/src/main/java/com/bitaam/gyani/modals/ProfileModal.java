package com.bitaam.gyani.modals;

import java.io.Serializable;
import java.util.List;

public class ProfileModal implements Serializable {

    public static final int TYPE_NORMAL_USER = 0;
    public static final int TYPE_ORGANISATION = 1;
    public static final int TYPE_ADVERTISER = 2;
    public static final int TYPE_COMPANY = 3;

    private String name,bio,gender,location,profileDate;
    private int age,type;
    private boolean privacy;
    private List<String> interests;

    public ProfileModal() {
    }

    public ProfileModal(String name, String bio, String gender, String location, int age, int type, boolean privacy, List<String> interests) {
        this.name = name;
        this.bio = bio;
        this.gender = gender;
        this.location = location;
        this.age = age;
        this.type = type;
        this.privacy = privacy;
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileDate() {
        return profileDate;
    }

    public void setProfileDate(String profileDate) {
        this.profileDate = profileDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
