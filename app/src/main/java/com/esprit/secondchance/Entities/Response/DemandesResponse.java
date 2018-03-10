package com.esprit.secondchance.Entities.Response;

import com.esprit.secondchance.Entities.Demande;

import java.util.List;

/**
 * Created by sofien on 09/12/2017.
 */

public class DemandesResponse {
    boolean success;
    String message;
    List<Demande> demandes;

    public DemandesResponse(boolean success, String message, List<Demande> demandes) {
        this.success = success;
        this.message = message;
        this.demandes = demandes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
}
