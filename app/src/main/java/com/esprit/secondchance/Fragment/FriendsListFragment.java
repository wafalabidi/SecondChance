package com.esprit.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.GridViewFrindAdapter;
import com.esprit.secondchance.Adapters.friendsListAdapter;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.FriendsListActivity;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 18/01/2018.
 */

public class FriendsListFragment extends Fragment {
    GridViewFrindAdapter adapter;
    ListView gvfriendsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_list_fragment, container, false);
        gvfriendsList = view.findViewById(R.id.gvfriendsList);
        getFriends();
        return view;
    }

    private void getFriends() {
        ProgressDialogAlternative.ShowDIalog(getActivity());
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface getFriends = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = getFriends.getFriendList(localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                ArrayList<Demande> list = (ArrayList<Demande>) response.body().getDemandes();
                if (list != null) {
                    adapter = new GridViewFrindAdapter(getActivity(), list);
                    gvfriendsList.setAdapter(adapter);
                }
                ProgressDialogAlternative.Dissmiss();

            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                ProgressDialogAlternative.Dissmiss();

            }
        });
    }
}
