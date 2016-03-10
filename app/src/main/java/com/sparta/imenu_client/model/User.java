package com.sparta.imenu_client.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private Date joinDate;
    private String country;
    private String city;
    private String picture;
    private List<String> preferences;
    private List<UserSpec> healthIssues;
    private List<UserSpec> restrictions;

    public User(String name, String email, String password, String gender, Date dateOfBirth, String country, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public List<UserSpec> getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(List<UserSpec> healthIssues) {
        this.healthIssues = healthIssues;
    }

    public List<UserSpec> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<UserSpec> restrictions) {
        this.restrictions = restrictions;
    }

    //to be implemented
    public void addPreference(String preference){

    }

    //to be implemented
    public boolean findPreference(String preference){
        return false;
    }

    //to be implemented
    public void removePreference(String preference){

    }

    //to be implemented
    public void addHealthIssue(UserSpec healthIssue){

    }

    //to be implemented
    public void removeHealthIssue(String healthIssueName){

    }

    //to be implemented
    public void addRestriction(UserSpec restriction){

    }

    //to be implemented
    public void removeRestriction(String restrictionName){

    }

    //to be implemented
    public String isHazardous(String keyword){
        return "";
    }

    //to be implemented
    public String isProhibited(String keyword){
        return "";
    }
}
