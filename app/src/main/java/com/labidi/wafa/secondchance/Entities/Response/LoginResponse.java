package com.labidi.wafa.secondchance.Entities.Response;

import com.labidi.wafa.secondchance.Entities.User;

/**
 * Created by Wafa on 19/11/2017.
 */

public class LoginResponse {
    boolean success;
    String message ;
    User user ;

    public LoginResponse() {

    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }

    public LoginResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
