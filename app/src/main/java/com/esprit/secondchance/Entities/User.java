package com.esprit.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wafa on 19/11/2017.
 */

public class User{
    public static String FirstName;
    public static String LastName;
    public static String Password;
    public static String Mail;
    public static String BirthDate;
    public static String Eyes;
    public static String Shape;
    public static String SkinColour;
    public static String gender;
    public static String Work;
    public static String Studies;
    public static String Hobbies;
    public static int Id;
    public static String imgprofile;
    public static String imgcouverture;
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


    @SerializedName("Shape")
    @Expose
    String shape;
    @SerializedName("SkinColour")
    @Expose
    String skinColour;

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
    @SerializedName("Img_profile")
    @Expose
    String img_profile;
    @SerializedName("Img_couverture")
    @Expose
    String img_couverture;
    @SerializedName("Country")
    @Expose
    String country;



    public User() {
    }

    public User(String randomId, String randomName, String randomAvatar, boolean randomBoolean) {
    }

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


    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
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

    public String getImg_couverture() {
        return img_couverture;
    }

    public void setImg_couverture(String img_couverture) {
        this.img_couverture = img_couverture;
    }



    public String getName() {
        return firstName + " " + lastName;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String firstName, String lastName, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.img_profile = avatar;
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
                ", shape='" + shape + '\'' +
                ", skinColour='" + skinColour + '\'' +
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

    public static boolean isSet() {
        if (User.Id != 0 && User.FirstName != "") {
            return false;
        }
        return false;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static void Disconect() {
        FirstName = "";
        LastName = "";
        Password = "";
        Mail = "";
        BirthDate = "";
        Eyes = "";
        Shape = "";
        SkinColour = "";
        Work = "";
        Studies = "";
        Hobbies = "";
        Id = 0;
        imgprofile = "";
        imgcouverture = "";
    }
}