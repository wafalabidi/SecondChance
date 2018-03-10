package com.esprit.secondchance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.friendsListAdapter;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 12/12/2017.
 */

public class FriendsListActivity extends BaseDrawerActivity implements SearchView.OnQueryTextListener {
    @BindView(R.id.rvFriendsList)
    RecyclerView rvFriendList;
    friendsListAdapter adapter;
    //No friends imageview
    @BindView(R.id.imageView3)
    ImageView imageView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list_activity);
        ButterKnife.bind(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFriendList.setLayoutManager(layoutManager);
        getFriends();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_toolbarSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void getFriends() {
        ProgressDialogAlternative.ShowDIalog(this);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface getFriends = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = getFriends.getFriendList(localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                ArrayList<Demande> list = (ArrayList<Demande>) response.body().getDemandes();
                if (list != null) {
                    adapter = new friendsListAdapter(FriendsListActivity.this, list);
                    rvFriendList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    imageView3.setVisibility(View.VISIBLE);
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
