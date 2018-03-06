package com.esprit.secondchance.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.R;

/**
 * Created by sofien on 21/11/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<User> {
    Context context;
    int layout;
    User[] users;
    ImageView ivFriendPicture;
    TextView tvFriendsName;
    ImageButton ibTextFriends;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull User[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        users = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, null);
        }
        ivFriendPicture = (ImageView) convertView.findViewById(R.id.ivFriendsList);
        tvFriendsName = (TextView) convertView.findViewById(R.id.tvFriendsNameRow);
        ivFriendPicture.setImageResource(R.drawable.unknown);
        String name = users[position].getFirstName() + " " + users[position].getLastName();
        tvFriendsName.setText(name);
        return convertView;
    }

    @Override
    public int getCount() {
        return users.length;
    }
}
