package com.esprit.secondchance.Entities.Response;

import com.esprit.secondchance.Entities.Like;

import java.util.ArrayList;

/**
 * Created by sofien on 08/01/2018.
 */

public class LikesResponse {
    private boolean isSucces ;
    private String message ;
    private ArrayList<Like> likes  ;

    public LikesResponse() {
    }

    public LikesResponse(boolean isSucces, String message, ArrayList<Like> likes) {
        this.isSucces = isSucces;
        this.message = message;
        this.likes = likes;
    }

    public boolean isSucces() {
        return isSucces;
    }

    public void setSucces(boolean succes) {
        isSucces = succes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }
}
