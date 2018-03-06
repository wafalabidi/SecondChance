package com.esprit.secondchance.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.R;
import com.esprit.secondchance.UserProfileActivity;
import com.esprit.secondchance.Utils.LocalFiles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 18/01/2018.
 */

public class GridViewFrindAdapter extends BaseAdapter {
    Context context;
    ArrayList<Demande> items;

    public GridViewFrindAdapter(Context activity, ArrayList<Demande> users) {
        this.context = activity;
        items = users;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getSender().getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.gr_friends_list, viewGroup, false);
        User user = items.get(i).getSender();
        TextView tvFriendsNameRow = view.findViewById(R.id.tvFriendsNameRow);
        ImageView ivFriendsList = view.findViewById(R.id.ivFriendsList);
        String string = user.getFirstName() + "  " + user.getLastName();
        tvFriendsNameRow.setText(string);
        String url = RetrofitClient.BASE_URL + user.getImg_profile();
        if (user.getImg_profile() != "" )
        ((Runnable) () -> Picasso.with(context).load(url).fit().into(ivFriendsList)).run();
        //TODO WAFA HOT taswira ya petite salope

        ivFriendsList.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra("idUser", user.getId());
            context.startActivity(intent);
        });
        Button unfRiendsButton = view.findViewById(R.id.unfriends);
        unfRiendsButton.setOnClickListener(v -> unFriend(user, i));
        return view;
    }

    private void unFriend(User user, int position) {
        LocalFiles localFiles = new LocalFiles(context.getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.refuserDemande(user.getId(), localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                items.remove(position);
                GridViewFrindAdapter.this.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });

    }
}
