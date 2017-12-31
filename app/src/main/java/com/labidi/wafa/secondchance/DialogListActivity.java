package com.labidi.wafa.secondchance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.labidi.wafa.secondchance.API.DiscussionDeserializer;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.PhotosAdapter;
import com.labidi.wafa.secondchance.Entities.Discussion;
import com.labidi.wafa.secondchance.Entities.Messages;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.DiscussionResponse;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static retrofit2.Converter.*;

/**
 * Created by macbook on 29/12/2017.
 */

public class DialogListActivity extends DialogsActivity {
    List<Discussion> discussion;

    private ArrayList<Discussion> dialogs;

    public static void open(Context context) {
        context.startActivity(new Intent(context, DialogListActivity.class));
    }

    private DialogsList dialogsList;

    private Factory createGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Discussion.class, new DiscussionDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        getDiscussion();
        }

    @Override
    public void onDialogClick(Discussion dialog) {
        DialogListActivity.open(this);
    }

    private void initAdapter() {

        ArrayList<Discussion> discussions = new  ArrayList<Discussion> () ;
        super.dialogsAdapter = new DialogsListAdapter<>(super.imageLoader);
        super.dialogsAdapter.setItems(discussions);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    //for example
    private void onNewMessage(String dialogId, Messages message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    //for example
    private void onNewDialog(Discussion dialog) {
        dialogsAdapter.addItem(dialog);
    }

    private void getDiscussion() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .client(client)
                .addConverterFactory(createGsonConverter())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        UserService.getDiscussion service = retrofit.create(UserService.getDiscussion.class);
        Call<DiscussionResponse> call = service.getDiscussion(User.Id);
        call.enqueue(new Callback<DiscussionResponse>() {

            @Override
            public void onResponse(Call<DiscussionResponse> call, Response<DiscussionResponse> response) {
                Log.e("luis", response.body().toString());
            }

            @Override
            public void onFailure(Call<DiscussionResponse> call, Throwable t) {

            }
        });

}
}