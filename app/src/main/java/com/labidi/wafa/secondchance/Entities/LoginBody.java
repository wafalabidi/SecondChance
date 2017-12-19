package com.labidi.wafa.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sofien on 13/12/2017.
 */

public class LoginBody {

    @Expose
    @SerializedName("mail")
    String mail;
    @SerializedName("password")
    @Expose
    String password;


    public LoginBody() {

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


    public LoginBody(String mail, String password) {

        this.mail = mail;
        this.password = password;

    }
}
