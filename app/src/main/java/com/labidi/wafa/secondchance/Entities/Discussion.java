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

    private int unreadCount;

    public Discussion(String id, String name, String photo,
                      ArrayList<User> users, Messages lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }
    public Discussion(String id, String name ){

        this.id=id;
        this.dialogName = name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getDialogPhoto() {
        return null;
    }

    @Override
    public String getDialogName() {
        return null;
    }

    @Override
    public List<? extends IUser> getUsers() {
        return null;
    }

    @Override
    public Messages getLastMessage() {
        return new Messages();
    }

    @Override
    public void setLastMessage(Messages message) {

    }

    @Override
    public int getUnreadCount() {
        return 0;
    }
}
