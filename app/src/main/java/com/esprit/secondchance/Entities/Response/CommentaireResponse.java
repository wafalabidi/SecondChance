package com.esprit.secondchance.Entities.Response;

import com.esprit.secondchance.Entities.Commentaire;

import java.util.ArrayList;

/**
 * Created by sofien on 19/12/2017.
 */

public class CommentaireResponse {
    ArrayList<Commentaire>  coms ;
    boolean isSucces ;
    String message ;

    public ArrayList<Commentaire> getComs() {
        return coms;
    }

    public void setComs(ArrayList<Commentaire> coms) {
        this.coms = coms;
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
}
