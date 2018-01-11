package com.labidi.wafa.secondchance.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

    public PendingInvitationAdapter(ArrayList<Demande> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pending_friend_request_row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Demande user = items.get(position);
        holder.tvName.setText(user.getSender().getFirstName() + "  " + user.getSender().getLastName());
        if (!TextUtils.isEmpty(user.getSender().getImg_profile())) {
            Picasso.with(context).load(user.getSender().getImg_profile()).into(holder.imageView);
        }

        holder.ibAccept.setOnClickListener(view -> {
            accepterDemande(items.get(position).getIdUser(), User.Id, position);
        });
        holder.ibRefuse.setOnClickListener(view -> {
            refuserDemande(items.get(position).getIdUser(), User.Id, position);
        });
    }

    private void accepterDemande(int idSender, int idReciver, int position) {
        items.remove(position);
        Toast.makeText(context , "Invitation Rejected" , Toast.LENGTH_SHORT).show();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface accept = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = accept.accepterDemande(idSender, idReciver);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                PendingInvitationAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Invitation accepted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });
    }

    private void refuserDemande(int idSender, int idReciver, int postion) {
        items.remove(postion);
        Toast.makeText(context , "Invitation Rejected" , Toast.LENGTH_SHORT).show();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface accept = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = accept.refuserDemande(idSender, idReciver);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                PendingInvitationAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Invitation accepted", Toast.LENGTH_SHORT).show();
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

}
