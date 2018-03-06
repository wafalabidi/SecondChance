package com.esprit.secondchance.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.BaseDrawerActivity;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 12/12/2017.
 */

public class PendingInvitationAdapter extends RecyclerView.Adapter<PendingInvitationAdapter.Holder> {
    ArrayList<Demande> items;
    Context context;
    LocalFiles localFiles;
    private int layout;

    public PendingInvitationAdapter(ArrayList<Demande> items, Context context, int layout) {
        this.items = items;
        this.context = context;
        localFiles = new LocalFiles(context.getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        this.layout = layout;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Demande user = items.get(position);
        if (items.get(position).getSender() != null) {
            String name = user.getSender().getFirstName() + "  " + user.getSender().getLastName();
            holder.tvName.setText(name);
            if (!TextUtils.isEmpty(user.getSender().getImg_profile())) {
                Picasso.with(context).load(RetrofitClient.BASE_URL + user.getSender().getImg_profile()).into(holder.imageView);
            }

            holder.ibAccept.setOnClickListener(view -> {
                int currentID = localFiles.getInt(LocalFiles.Id);
                int userId = items.get(position).getIdUser();
                int userId2 = items.get(position).getIdUser2();
                if (currentID == userId)
                    accepterDemande(userId2, currentID, position);
                else
                    accepterDemande(userId, currentID, position);

            });
            holder.ibRefuse.setOnClickListener(view -> {
                int currentID = localFiles.getInt(LocalFiles.Id);
                int userId = items.get(position).getIdUser();
                int userId2 = items.get(position).getIdUser2();
                if (currentID == userId)
                    refuserDemande(userId2, currentID, position);
                else
                    refuserDemande(userId, currentID, position);

            });
        }
    }

    private void accepterDemande(int idSender, int idReciver, int position) {
        items.remove(position);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface accept = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = accept.accepterDemande(idSender, idReciver);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                PendingInvitationAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Invitation accepted", Toast.LENGTH_SHORT).show();
                refreshIcons();

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });
    }

    private void refuserDemande(int idSender, int idReciver, int postion) {
        items.remove(postion);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface accept = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = accept.refuserDemande(idSender, idReciver);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                PendingInvitationAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Invitation Rejected", Toast.LENGTH_SHORT).show();
                refreshIcons();
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton ibAccept;
        ImageButton ibRefuse;
        TextView tvName;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            ibAccept = itemView.findViewById(R.id.ibAccept);
            ibRefuse = itemView.findViewById(R.id.ibRefuse);
            tvName = itemView.findViewById(R.id.username);
        }
    }

    public void refreshIcons() {
        ((BaseDrawerActivity) context).getCurrentUserDemands();
    }
}
