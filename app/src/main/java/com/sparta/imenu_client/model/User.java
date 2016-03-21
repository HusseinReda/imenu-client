package com.sparta.imenu_client.model;

import com.sparta.imenu_client.service.HealthIssueService;
import com.sparta.imenu_client.service.PreferenceService;
import com.sparta.imenu_client.service.RestrictionService;

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
    private List<Review> reviews;

    public User(String name, String email, String password, String gender, Date dateOfBirth, String country, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
    }

    public User(String name, String email, String password, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
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

    public void addPreference(String preference){
        PreferenceService addPreferenceService = new PreferenceService(getId(),preference,"add");
        if(addPreferenceService.getResult()== "OK")
            getPreferences().add(preference);
    }

    public boolean findPreference(String preference){
        return getPreferences().indexOf(preference) != -1;
    }

    public void removePreference(String preference){
        int exist = getPreferences().indexOf(preference);
        if(exist!=-1) {
            PreferenceService removePreferenceService = new PreferenceService(getId(),preference,"remove");
            if(removePreferenceService.getResult()=="OK")
                getPreferences().remove(preference);
        }
    }

    public void addHealthIssue(UserSpec healthIssue){
        HealthIssueService addHealthIssueService = new HealthIssueService(getId(),healthIssue,"add");
        if(addHealthIssueService.getResult()=="OK")
            getHealthIssues().add(healthIssue);
    }

    public void removeHealthIssue(String healthIssueName){
        List<UserSpec> healthIssues = getHealthIssues();
        UserSpec healthIssue;
        for(int i=0;i<healthIssues.size();i++){
            if(healthIssues.get(i).getName().equals(healthIssueName)) {
                healthIssue = healthIssues.get(i);
                HealthIssueService removeHealthIssueService = new HealthIssueService(getId(),healthIssue,"remove");
                if(removeHealthIssueService.getResult()=="OK")
                    getHealthIssues().remove(healthIssue);
                break;
            }
        }
    }

    //to be implemented
    public void addRestriction(UserSpec restriction){
        RestrictionService addRestrictionService = new RestrictionService(getId(),restriction,"add");
        if(addRestrictionService.getResult()=="OK")
            getRestrictions().add(restriction);
    }

    //to be implemented
    public void removeRestriction(String restrictionName){
        List<UserSpec> restrictions = getRestrictions();
        UserSpec restriction;
        for(int i=0;i<restrictions.size();i++){
            if(restrictions.get(i).getName().equals(restrictionName)) {
                restriction = restrictions.get(i);
                RestrictionService removeRestrictionService = new RestrictionService(getId(), restriction,"remove");
                if(removeRestrictionService.getResult()=="OK")
                    getHealthIssues().remove(restriction);
                break;
            }
        }
    }

    //to be implemented
    public String isHazardous(String keyword){
        for(int i=0;i<getHealthIssues().size();i++){
            if(getHealthIssues().get(i).getName().equals(keyword)) {
                return getHealthIssues().get(i).getNote();
            }
        }
        return "safe";
    }

    //to be implemented
    public String isProhibited(String keyword){
        for(int i=0;i<getRestrictions().size();i++){
            if(getRestrictions().get(i).getName().equals(keyword)) {
                return getRestrictions().get(i).getNote();
            }
        }
        return "safe";
    }
}
