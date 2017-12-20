package com.labidi.wafa.secondchance.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.labidi.wafa.secondchance.Entities.Commentaire;
import com.labidi.wafa.secondchance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sofien on 19/12/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {

    Context context;
    ArrayList<Commentaire> items;

    public CommentAdapter(Context context, ArrayList<Commentaire> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.comment_row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Commentaire commentaire = items.get(position);
        if(!TextUtils.isEmpty(commentaire.getImg_profile())){
            Picasso.with(context).load(commentaire.getImg_profile()).into(holder.ivRowComment);
        }
        String name = commentaire.getFirstName() + "  "+ commentaire.getLastName();
        holder.tvRowCommentName.setText(name);
        holder.tvRowCommentSaying.setText(commentaire.getSayin());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvRowCommentName;
        TextView tvRowCommentSaying;
        CircleImageView ivRowComment;

        Holder(View itemView) {
            super(itemView);
            tvRowCommentName = itemView.findViewById(R.id.tvRowCommentName);
            tvRowCommentSaying = itemView.findViewById(R.id.tvRowCommentSaying);
            ivRowComment = itemView.findViewById(R.id.ivRowComment);
        }
    }
}
