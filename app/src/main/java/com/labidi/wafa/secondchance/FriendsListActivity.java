package com.labidi.wafa.secondchance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.friendsListAdapter;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.User;

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
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface getFriends = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<DemandesResponse> call = getFriends.getFriendList(User.Id);
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                ArrayList<Demande> list = (ArrayList<Demande>) response.body().getDemandes();
                if (list != null) {
                    adapter = new friendsListAdapter(FriendsListActivity.this , list) ;
                    rvFriendList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {

            }
        });
    }
}
