package com.esprit.secondchance.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.secondchance.Entities.Post;
import com.esprit.secondchance.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sofien on 20/11/2017.
 */

public class RvHomeAdapter extends RecyclerView.Adapter<RvHomeAdapter.HomeViewAdapter> {
    List<Post> items;
    Context context;

    public List<Post> getItems() {
        return items;
    }

    public void setItems(List<Post> items) {
        this.items = items;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RvHomeAdapter(Context context, List<Post> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public RvHomeAdapter.HomeViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_row, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new HomeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(HomeViewAdapter holder, int position) {
        // if (items.get(position) != null) {
        String saying = items.get(position).getSaying();
        String name = items.get(position).getUser().getFirstName() + " " + items.get(position).getUser().getLastName();
        Picasso.with(context).load(items.get(position).getImage()).into(holder.ivRow);
        holder.tvSayaing.setText(saying);
        holder.tvUserName.setText(name);
        //}
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class HomeViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.ivRow)
        ImageView ivRow;
        @BindView(R.id.ibDislike)
        ImageButton ibDislike;
        @BindView(R.id.ibLike)
        ImageButton ibLike;
        @BindView(R.id.tvSaying)
        TextView tvSayaing;
        @BindView(R.id.tvUserName)
        TextView tvUserName;

        public HomeViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivRow = (ImageView) itemView.findViewById(R.id.ivRow);
            ibDislike = (ImageButton) itemView.findViewById(R.id.ibDislike);
            ibLike = (ImageButton) itemView.findViewById(R.id.ibLike);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvSayaing = (TextView) itemView.findViewById(R.id.tvSaying);

        }
    }
}
