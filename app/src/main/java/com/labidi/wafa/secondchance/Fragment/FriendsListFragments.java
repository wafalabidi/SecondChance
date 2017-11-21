package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.labidi.wafa.secondchance.Adapters.CustomArrayAdapter;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.R;

/**
 * Created by sofien on 21/11/2017.
 */

public class FriendsListFragments extends Fragment {
    GridView gvFriendsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = inflater.inflate(R.layout.friend_list_fragment, null);
        gvFriendsList = view.findViewById(R.id.gvfriendsList);
        User[] users = {new User("Frank ", "Riberry"), new User("Jean Claude", " mouch Vandame"), new User("Mark ", "ZuckElberg")};

        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(getContext(), R.layout.gr_friends_list, users);
        gvFriendsList.setAdapter(customArrayAdapter);

        return view;
    }

}
