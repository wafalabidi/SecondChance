package com.labidi.wafa.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sofien on 19/12/2017.
 */

public class Commentaire {
    @Expose
    @SerializedName("Sayin")
    String sayin;
    @Expose
    @SerializedName("IdPost")
    int idPost;
    @Expose
    @SerializedName("IdCom")
    int idCom ;
    @Expose
    @SerializedName("IdUser")
    int idUser ;
    @Expose
    @SerializedName("user")
    User user ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Commentaire() {

    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getSayin() {
        return sayin;
    }

    public void setSayin(String sayin) {
        this.sayin = sayin;
    }
}
