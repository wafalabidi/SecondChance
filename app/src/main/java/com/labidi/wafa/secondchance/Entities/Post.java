package com.labidi.wafa.secondchance.Entities;

import android.graphics.Bitmap;

/**
 * Created by sofien on 20/11/2017.
 */

public class Post {
    private Bitmap image ;
    private String saying ;
    private int id ;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post(Bitmap image, String saying) {
        this.image = image;
        this.saying = saying;
    }
}
