package com.labidi.wafa.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sofien on 19/12/2017.
 */

public class Commentaire extends User {
    @Expose
    @SerializedName("Sayin")
    String sayin;
    @Expose
    @SerializedName("IdPost")
    int idPost;

    public Commentaire() {

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
