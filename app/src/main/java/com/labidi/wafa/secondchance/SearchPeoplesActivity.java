package com.labidi.wafa.secondchance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    @BindView(R.id.searchLayout)
    ConstraintLayout searchLayout;

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
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);
        searchLayout.addView(progressBar, params);
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        retrofit2.Call<SearchResponse> call = registerInterface.searchUser(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(retrofit2.Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.e("All good ", "Checkpoint 1 ");
                if (response != null) {
                    if (response.body().getUsers() != null) {
                        for (User u : response.body().getUsers()
                                ) {
                            u.setImg_profile(RetrofitClient.BASE_URL + u.getImg_profile());
                        }

                        adapter = new ResearchResultAdapter(SearchPeoplesActivity.this, response.body().getUsers());
                        adapter.setFriendRequests((ArrayList<Demande>) friendList);
                        rvSearchResult.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBar.setVisibility(View.GONE);
                    }
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
        Log.e("User ID ", User.Id +"aa ");
        Call<DemandesResponse> call = registerInterface.checkInvitationById(User.Id);
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                progressDialog.dismiss();
                if (response.body().getDemandes() != null) {
                    friendList = response.body().getDemandes();
                    for (Demande d: friendList
                         ) {
                        Log.e("Demande" , d.toString());
                    }
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
