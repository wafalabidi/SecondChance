package com.labidi.wafa.secondchance.Entities;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

/**
 * Created by macbook on 29/12/2017.
 */

public class Messages implements IMessage,
        MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {

        private String  id;
        private String text;
        private Date createdAt;
        private User user;
        private Messages.Image image;

    @Override
    public String getImageUrl() {
        return "https://thumb1.shutterstock.com/display_pic_with_logo/166387726/713065900/stock-vector-boo-sign-713065900.jpg";
    }

    @Override
    public String getId() {
        return "3";
    }

    @Override
    public String getText() {
        return "psika";
    }

    @Override
    public IUser getUser() {
       return  new User("psika","psk");
    }

    @Override
    public Date getCreatedAt() {
        return new Date();
    }
}
