package com.esprit.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sofien on 08/01/2018.
 */

public class Like {
    @SerializedName("idPost")
    @Expose
    private Integer idPost;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        return idPost.equals(like.idPost) && idUser.equals(like.idUser);
    }

    @Override
    public int hashCode() {
        int result = idPost.hashCode();
        result = 31 * result + idUser.hashCode();
        return result;
    }

    public Like() {
    }

    public Like(Integer idPost, Integer idUser) {
        this.idPost = idPost;
        this.idUser = idUser;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}
