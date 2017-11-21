package com.labidi.wafa.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wafa on 19/11/2017.
 */

public class User {
    @SerializedName("FirstName")
    @Expose
    String firstName;

    @SerializedName("LastName")
    @Expose
    String lastName;

    @SerializedName("Password")
    @Expose
    String password;

    @SerializedName("Mail")
    @Expose
    String mail;

    @SerializedName("Id")
    @Expose
    int id;

    @SerializedName("BirthDate")
    @Expose
    String birthDate;

    @SerializedName("Eyes")
    @Expose
    String eyes;
    @SerializedName("Size")
    @Expose
    String size;
    @SerializedName("Weight")
    @Expose
    String weight;
    @SerializedName("Shape")
    @Expose
    String shape;
    @SerializedName("SkinColour")
    @Expose
    String skinColour;
    @SerializedName("Alchool")
    @Expose
    String alchool;
    @SerializedName("Tobaco")
    @Expose
    String tobaco;
    @SerializedName("Drug")
    @Expose
    String drug;
    @SerializedName("Work")
    @Expose
    String work;
    @SerializedName("Studies")
    @Expose
    String studies;
    @SerializedName("Child")
    @Expose
    String child;
    @SerializedName("Hobbies")
    @Expose
    String hobbies;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(String skinColour) {
        this.skinColour = skinColour;
    }

    public String getAlchool() {
        return alchool;
    }

    public void setAlchool(String alchool) {
        this.alchool = alchool;
    }

    public String getTobaco() {
        return tobaco;
    }

    public void setTobaco(String tobaco) {
        this.tobaco = tobaco;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getStudies() {
        return studies;
    }

    public void setStudies(String studies) {
        this.studies = studies;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", id=" + id +
                ", birthDate='" + birthDate + '\'' +
                ", eyes='" + eyes + '\'' +
                ", size='" + size + '\'' +
                ", weight='" + weight + '\'' +
                ", shape='" + shape + '\'' +
                ", skinColour='" + skinColour + '\'' +
                ", alchool='" + alchool + '\'' +
                ", tobaco='" + tobaco + '\'' +
                ", drug='" + drug + '\'' +
                ", work='" + work + '\'' +
                ", studies='" + studies + '\'' +
                ", child='" + child + '\'' +
                ", hobbies='" + hobbies + '\'' +
                '}';
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
