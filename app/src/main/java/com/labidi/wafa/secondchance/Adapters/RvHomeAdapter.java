package com.labidi.wafa.secondchance.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sofien on 20/11/2017.
 */

public class RvHomeAdapter extends RecyclerView.Adapter<RvHomeAdapter.HomeViewAdapter> {
    ArrayList<Post> items;
    Context context;

    public RvHomeAdapter(Context context, ArrayList<Post> items) {
        this.context = context;
        //if(items!= null)
        this.items = new ArrayList<>();
        this.items.add(new Post(null, "Ok ok "));
        this.items.add(new Post(null, "Ok ok "));
        this.items.add(new Post(null, "Ok ok "));
        this.items.add(new Post(null, "Ok ok "));
        this.items.add(new Post(null, "Ok ok "));
    }

    @Override
    public RvHomeAdapter.HomeViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_row, null, false);
        HomeViewAdapter viewAdapter = new HomeViewAdapter(view);

        return viewAdapter;
    }

    @Override
    public void onBindViewHolder(HomeViewAdapter holder, int position) {
       // if (items.get(position) != null) {
            String saying = items.get(position).getSaying();
            String name = "Mohamed Ali clay";
            holder.ivRow.setImageResource(R.drawable.unknown);
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
            ivRow = (ImageView) itemView.findViewById(R.id.ivRow) ;
            ibDislike = (ImageButton) itemView.findViewById(R.id.ibDislike) ;
            ibLike = (ImageButton)itemView.findViewById(R.id.ibLike) ;
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvSayaing = (TextView)itemView.findViewById(R.id.tvSaying);

        }
    }
}
