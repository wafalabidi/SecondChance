package com.esprit.secondchance.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sofien on 09/12/2017.
 */

public class Demande {
    public static int friend;


    @SerializedName("idUser")
    @Expose
    int idUser;
    @SerializedName("idUser2")
    @Expose
    int idUser2;
    @SerializedName("state")
    @Expose
    int state;
    @SerializedName("sender")
    private  User sender ;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "idUser=" + idUser +
                ", idUser2=" + idUser2 +
                ", state=" + state +
                '}';
    }

    public Demande(int idUser, int idUser2, int state) {
        this.idUser = idUser;
        this.idUser2 = idUser2;
        this.state = state;
    }

    public Demande() {

    }

    public Demande(int id, int id1) {
        idUser = id;
        idUser2 = id1;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demande that = (Demande) o;

        if (idUser != that.idUser) return false;
        return idUser2 == that.idUser2;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + idUser2;
        return result;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
