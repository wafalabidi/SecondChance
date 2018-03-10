package com.esprit.secondchance.Entities;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;

/**
 * Created by sofien on 20/11/2017.
 */

public class Post {
    public static String img;

    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("IdPost")
    @Expose
    private int idPost;
    @SerializedName("IdUsers")
    @Expose
    private int idUser;
    @SerializedName("Likes")
    @Expose
    private int likes;
    @SerializedName("user")
    @Expose
    private User user ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", saying='" + description + '\'' +

                ", idUser=" + idUser +
                ", likes=" + likes +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSaying() {
        return description;
    }

    public void setSaying(String saying) {
        this.description = saying;
    }

    public int getidPost() {
        return idPost;
    }

    public void setidPost(int id) {
        this.idPost = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void convertImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        this.image = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);

    }
}
