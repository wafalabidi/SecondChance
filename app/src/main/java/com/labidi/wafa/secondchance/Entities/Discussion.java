package com.labidi.wafa.secondchance.Entities;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 29/12/2017.
 */

public class Discussion implements IDialog<Messages> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<User> users;
    private Messages lastMessage;

    public Discussion(String id, String name, String photo,
                      ArrayList<User> users, Messages lastMessage) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
    }


    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto ;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<? extends IUser> getUsers() {
        return users;
    }

    @Override
    public Messages getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Messages message) {

    }

    @Override
    public int getUnreadCount() {
        return 0;
    }
}
