package com.labidi.wafa.secondchance.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sofien on 12/12/2017.
 */

public class friendsListAdapter extends RecyclerView.Adapter<friendsListAdapter.Holder> {
    ArrayList<Demande> demandes;
    Context context;

    public friendsListAdapter(Context context, ArrayList<Demande> demandes) {
        this.demandes = demandes;
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.friends_list_row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Demande demande = demandes.get(position);
        if (!TextUtils.isEmpty(demande.getImg_profile())) {
            Picasso.with(context).load(demande.getImg_profile()).into(holder.image);
        }
        holder.nom.setText(demande.getFirstName() + "  " + demande.getLastName());

    }

    @Override
    public int getItemCount() {
        return demandes.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageButton)
        ImageButton envoyerMessage;
        @BindView(R.id.imageButton3)
        ImageButton supprimerContact;
        @BindView(R.id.imageButton2)
        ImageButton ouvrirProfil;
        @BindView(R.id.ivUserProfile)
        ImageView image;
        @BindView(R.id.tvFriend)
        TextView nom;

        Holder(View itemView) {
            super(itemView);
            envoyerMessage = itemView.findViewById(R.id.imageButton);
            supprimerContact = itemView.findViewById(R.id.imageButton3);
            ouvrirProfil = itemView.findViewById(R.id.imageButton2);
            image = itemView.findViewById(R.id.ivUserProfile);
            nom = itemView.findViewById(R.id.tvFriend);

        }
    }
}