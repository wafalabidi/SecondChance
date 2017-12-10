package com.labidi.wafa.secondchance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.ResearchResultAdapter;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.Response.SearchResponse;
import com.labidi.wafa.secondchance.Entities.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 10/12/2017.
 */

public class SearchPeoplesActivity extends BaseDrawerActivity implements SearchView.OnQueryTextListener {
    List<Demande> friendList;

    private ResearchResultAdapter adapter;
    @BindView(R.id.rvSearchResult)
    RecyclerView rvSearchResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_peoples_activity);
        ButterKnife.bind(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchResult.setLayoutManager(layoutManager);
        checkInvitation();

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
        Search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void Search(String query) {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        retrofit2.Call<SearchResponse> call = registerInterface.searchUser(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(retrofit2.Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.e("All good ", "Checkpoint 1 ");
                if (response.body().getUsers() != null) {
                    for (User u : response.body().getUsers()
                            ) {
                        u.setImg_profile(RetrofitClient.BASE_URL + u.getImg_profile());
                    }

                    adapter = new ResearchResultAdapter(SearchPeoplesActivity.this, response.body().getUsers());
                    adapter.setFriendRequests((ArrayList<Demande>) friendList);
                    rvSearchResult.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SearchResponse> call, Throwable t) {
                Log.e("Erreur de la recherche", t.getMessage());
            }
        });
    }

    public void checkInvitation() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait .... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<DemandesResponse> call = registerInterface.checkInvitation(User.Id);
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                progressDialog.dismiss();
                if (response.body().getDemandes() != null) {
                    friendList = response.body().getDemandes();
                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("Erreur lors de l'envoie", t.getMessage());
                progressDialog.dismiss();

            }
        });
    }
}