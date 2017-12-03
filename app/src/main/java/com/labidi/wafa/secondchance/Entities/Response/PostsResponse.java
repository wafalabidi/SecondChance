package com.labidi.wafa.secondchance.Entities.Response;

import com.labidi.wafa.secondchance.Entities.Post;

import java.util.List;

/**
 * Created by sofien on 27/11/2017.
 */

public class PostsResponse {
    boolean success;
    String message;
    List<Post> post;

    public PostsResponse(boolean success, String message, List<Post> post) {
        this.success = success;
        this.message = message;
        this.post = post;
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

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
}
