package xyz.voltwilz.employee.ClassOnly;

import java.util.Date;

public class UserProfile {
    public String firstName, lastName, nickname, address, ctcNum1, ctcNum2, organization, orgDetail, typeBudget1, typeBudget2, colourRelation;
    public String profPicUrl, date_entry;
    public Integer salary, budget1, budget2, userLevel;

    public UserProfile(){

    }

    public UserProfile(String profPicUrl){
        this.profPicUrl = profPicUrl;
    }


    public UserProfile(String firstname, String lastname, String nickname, String address, String ctcNum1, String ctcNum2, String organization, String orgDetail, String typeBudget1, String typeBudget2, Integer salary, Integer budget1, Integer budget2, Integer userLevel) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.nickname = nickname;
        this.address = address;
        this.ctcNum1 = ctcNum1;
        this.ctcNum2 = ctcNum2;
        this.organization = organization;
        this.orgDetail = orgDetail;
        this.typeBudget1 = typeBudget1;
        this.typeBudget2 = typeBudget2;
        this.salary = salary;
        this.budget1 = budget1;
        this.budget2 = budget2;
        this.userLevel = userLevel;
    }

    public UserProfile(String firstname, String lastname, String nickname, String address, String ctcNum1, String ctcNum2, String organization, String orgDetail, String typeBudget1, String typeBudget2, Integer salary, Integer budget1, Integer budget2, Integer userLevel, String date_entry, String colourRelation) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.nickname = nickname;
        this.address = address;
        this.ctcNum1 = ctcNum1;
        this.ctcNum2 = ctcNum2;
        this.organization = organization;
        this.orgDetail = orgDetail;
        this.typeBudget1 = typeBudget1;
        this.typeBudget2 = typeBudget2;
        this.salary = salary;
        this.budget1 = budget1;
        this.budget2 = budget2;
        this.userLevel = userLevel;
        this.colourRelation = colourRelation;
        this.date_entry = date_entry;
    }

    public String getCtcNum1() {
        return ctcNum1;
    }

    public void setCtcNum1(String ctcNum1) {
        this.ctcNum1 = ctcNum1;
    }

    public String getCtcNum2() {
        return ctcNum2;
    }

    public void setCtcNum2(String ctcNum2) {
        this.ctcNum2 = ctcNum2;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrgDetail() {
        return orgDetail;
    }

    public void setOrgDetail(String orgDetail) {
        this.orgDetail = orgDetail;
    }

    public String getTypeBudget1() {
        return typeBudget1;
    }

    public void setTypeBudget1(String typeBudget1) {
        this.typeBudget1 = typeBudget1;
    }

    public String getTypeBudget2() {
        return typeBudget2;
    }

    public void setTypeBudget2(String typeBudget2) {
        this.typeBudget2 = typeBudget2;
    }

    public Integer getBudget1() {
        return budget1;
    }

    public void setBudget1(Integer budget1) {
        this.budget1 = budget1;
    }

    public Integer getBudget2() {
        return budget2;
    }

    public void setBudget2(Integer budget2) {
        this.budget2 = budget2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getProfPicUrl() {
        return profPicUrl;
    }

    public void setProfPicUrl(String profPicUrl) {
        this.profPicUrl = profPicUrl;
    }

    public String getDate_entry() {
        return date_entry;
    }

    public void setDate_entry(String date_entry) {
        this.date_entry = date_entry;
    }

    public String getColourRelation() {
        return colourRelation;
    }

    public void setColourRelation(String colourRelation) {
        this.colourRelation = colourRelation;
    }
}
