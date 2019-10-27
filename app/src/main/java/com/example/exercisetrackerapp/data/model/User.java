package com.example.exercisetrackerapp.data.model;

import java.util.List;

public class User {
    private String name;
    private String middleName;
    private String matSurname;
    private String patSurname;
    private String email;
    private Date dtOfBirth;
    private List<Weight> weights;
    private float height;
    private Gender gender;
    private Job occupation;
    private String password;

    public User(String name, String middleName, String matSurname, String patSurname, String email, Date dtOfBirth, List<Weight> weights, float height, Gender gender, Job occupation, String password) {
        this.name = name;
        this.middleName = middleName;
        this.matSurname = matSurname;
        this.patSurname = patSurname;
        this.email = email;
        this.dtOfBirth = dtOfBirth;
        this.weights = weights;
        this.height = height;
        this.gender = gender;
        this.occupation = occupation;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMatSurname() {
        return matSurname;
    }

    public void setMatSurname(String matSurname) {
        this.matSurname = matSurname;
    }

    public String getPatSurname() {
        return patSurname;
    }

    public void setPatSurname(String patSurname) {
        this.patSurname = patSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtOfBirth() {
        return dtOfBirth;
    }

    public void setDtOfBirth(Date dtOfBirth) {
        this.dtOfBirth = dtOfBirth;
    }

    public List<Weight> getWeights() {
        return weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Job getOccupation() {
        return occupation;
    }

    public void setOccupation(Job occupation) {
        this.occupation = occupation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
