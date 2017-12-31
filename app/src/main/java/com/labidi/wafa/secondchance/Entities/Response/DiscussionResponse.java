package com.labidi.wafa.secondchance.Entities.Response;

import com.labidi.wafa.secondchance.Entities.Discussion;
import com.labidi.wafa.secondchance.Entities.Post;

import java.util.List;

/**
 * Created by macbook on 30/12/2017.
 */

public class DiscussionResponse {
    boolean success;
    String message;
    List<Discussion> discussions;

    public DiscussionResponse(boolean success, String message, List<Discussion> discussions) {
        this.success = success;
        this.message = message;
        this.discussions = discussions;
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

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }
}
