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

    public Messages(String id, String text, Date createdAt, User user) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }

    @Override
    public String getImageUrl() {
        return user.getAvatar();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
     return user;
    }

    @Override
    public Date getCreatedAt() {
        return new Date();
    }
}
