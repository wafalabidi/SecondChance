package com.labidi.wafa.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wafa on 19/11/2017.
 */

public class InscriptionBody {
    @SerializedName("firstname")
    @Expose
    String firstName ;
    @SerializedName("lastname")
    @Expose
    String lastName ;
    @SerializedName("mail")
    @Expose
    String mail ;
    @SerializedName("password")
    @Expose
    String password ;
    @SerializedName("birthdate")
    @Expose
    String birthdate ;

    public InscriptionBody() {

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate= birthdate;
    }

    public InscriptionBody(String firstName, String lastName, String mail, String password, String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.birthdate= birthdate;
    }
}
