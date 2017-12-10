package com.labidi.wafa.secondchance.Entities.Response;

import com.labidi.wafa.secondchance.Entities.User;

import java.util.ArrayList;

/**
 * Created by sofien on 08/12/2017.
 */

public class SearchResponse {
    boolean success;
    String message;
    ArrayList<User> users ;

    public SearchResponse(boolean success, String message, ArrayList<User> users) {
        this.success = success;
        this.message = message;
        this.users = users;
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
