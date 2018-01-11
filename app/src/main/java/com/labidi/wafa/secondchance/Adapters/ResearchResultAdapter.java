package com.labidi.wafa.secondchance.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.UserProfileActivity;
import com.labidi.wafa.secondchance.Utils.LocalFiles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 08/12/2017.
 */

public class ResearchResultAdapter extends RecyclerView.Adapter<ResearchResultAdapter.Holder> {
    Context context;
    ArrayList<User> items;
    ArrayList<Demande> friendRequests;





    public ResearchResultAdapter(Context context, ArrayList<User> items, ArrayList<Demande> friendRequests) {
        this.items = items;
        this.context = context;
        this.friendRequests = friendRequests ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_result_row, null, false);
        Holder viewAdapter = new Holder(view);

        return viewAdapter;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User user = items.get(position);
        Picasso.with(context).load(user.getImg_profile()).into(holder.image);

        holder.text.setText(user.getFirstName() + " " + user.getLastName());
        if (checkFriend(user)) {
            holder.add.setEnabled(true);
            holder.add.setOnClickListener(view -> {
                LocalFiles localFiles = new LocalFiles(context.getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE) );

                sendInvitation(new Demande(localFiles.getInt(LocalFiles.Id), user.getId()), view);
            });
        } else {
            holder.add.setImageDrawable(context.getDrawable(R.drawable.ic_profil));
            holder.add.setOnClickListener(view -> {
                openProfil(view , user.getId());
            });
        }

    }

    public void openProfil(View v, int idUser) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, (Activity) context, idUser);
        ((Activity) context).overridePendingTransition(0, 0);
    }

    private boolean checkFriend(User user) {
        LocalFiles localFiles = new LocalFiles(context.getSharedPreferences(LocalFiles.USER_FILE , Context.MODE_PRIVATE));
        int currentUser = localFiles.getInt(LocalFiles.Id);
        Demande friendRequest1 = new Demande();
        friendRequest1.setIdUser(currentUser);
        friendRequest1.setIdUser2(user.getId());
        Demande friendRequest2 = new Demande();
        friendRequest2.setIdUser(user.getId());
        friendRequest2.setIdUser2(currentUser);
        if (friendRequests != null) {
            if (friendRequests.contains(friendRequest1) || friendRequests.contains(friendRequest2)) {
                return false;
            }
        }
        return true;
    }

    private void sendInvitation(Demande friendRequest, View view) {

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.sendRequest(friendRequest.getIdUser(), friendRequest.getIdUser2());
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Toast.makeText(context, "Invitation sent", Toast.LENGTH_SHORT).show();
                ((ImageButton) view).setImageDrawable(context.getDrawable(R.drawable.ic_profil));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "go to profil", Toast.LENGTH_SHORT).show();
                        //TODO go to profil
                    }
                });
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Log.e("Erreur lors de l'envoie", t.getMessage());

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageButton add;
        ImageView image;
        TextView text;

        Holder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.rvSearchResultAdd);
            image = itemView.findViewById(R.id.rvSearchResultImage);
            text = itemView.findViewById(R.id.rvSearchResultName);

        }
    }

}
