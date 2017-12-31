package com.labidi.wafa.secondchance.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.labidi.wafa.secondchance.Entities.Discussion;
import com.labidi.wafa.secondchance.Entities.Messages;
import com.labidi.wafa.secondchance.Entities.Response.DiscussionResponse;
import com.labidi.wafa.secondchance.Entities.User;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by macbook on 30/12/2017.
 */

public class DiscussionDeserializer implements JsonDeserializer<DiscussionResponse> {


    @Override
    public DiscussionResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Read simple String values.
        ArrayList<Discussion> result = new ArrayList<Discussion>();
        final JsonArray discussionsString = jsonObject.get("threads").getAsJsonArray();

        for(Object obj: discussionsString){
            if ( obj instanceof JsonObject ) {
                JsonObject object = (JsonObject)obj;
                final String id = object.get("id").getAsString();
                final String firstName = object.get("firstName").getAsString();
                final String lastMesssageID = object.get("lastMessage").getAsString();
                final String lastName = object.get("lastName").getAsString();
                final String imageUrl = object.get("Img_profile").getAsString();
                final String messageBody = object.get("body").getAsString();
                final String messageDate = object.get("createdAt").getAsString();
                final User user = new User(firstName, lastName, imageUrl);
                final Messages lastMessage = new Messages(lastMesssageID, messageBody, new Date(), user);
                final ArrayList<User> arr = new ArrayList<User>();
                arr.add(user);
                final Discussion discussion = new Discussion(id, firstName + " " + lastName, imageUrl, arr, lastMessage);
                result.add(discussion);
            }
        }
        return new DiscussionResponse(true, ",", result);
    }

}
