package com.esprit.secondchance;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.PendingInvitationAdapter;
import com.esprit.secondchance.Adapters.ResearchResultAdapter;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Entities.Response.SearchResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;
import com.esprit.secondchance.Utils.SearchQueryProvider;

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
    SearchView searchView;
    private ResearchResultAdapter adapter;
    @BindView(R.id.rvSearchResult)
    RecyclerView rvSearchResult;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.pendingRequest)
    RecyclerView pendingRequest;

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
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, SearchPeoplesActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchQueryProvider.authority, SearchQueryProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }

    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, true);
        }
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

        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchQueryProvider.authority, SearchQueryProvider.MODE);
        suggestions.saveRecentQuery(query, null);

        ProgressDialogAlternative.ShowDIalog(this);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        retrofit2.Call<SearchResponse> call = registerInterface.searchUser(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.e("CheckPoint", "Chekcpoint ");
                ProgressDialogAlternative.Dissmiss();

                if (response.body().getUsers() != null) {
                    for (User u : response.body().getUsers()
                            ) {
                        u.setImg_profile(RetrofitClient.BASE_URL + u.getImg_profile());
                    }

                    Log.e("CheckPoint", "Chekcpoint ");
                    if (friendList != null) {
                        adapter = new ResearchResultAdapter(SearchPeoplesActivity.this, response.body().getUsers(), (ArrayList<Demande>) friendList);


                    } else {
                        adapter = new ResearchResultAdapter(SearchPeoplesActivity.this, response.body().getUsers(), new ArrayList<>());

                    }
                    rvSearchResult.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("Erreur de la recherche", t.getMessage());
                ProgressDialogAlternative.Dissmiss();

            }
        });
    }

    public void checkInvitation() {
        ProgressDialogAlternative.ShowDIalog(this);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = registerInterface.checkInvitationById(localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null) {
                    friendList = response.body().getDemandes();

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(SearchPeoplesActivity.this) {
                        @Override
                        public boolean supportsPredictiveItemAnimations() {
                            return true;
                        }
                    };
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    pendingRequest.setLayoutManager(layoutManager);
                    PendingInvitationAdapter adapter = new PendingInvitationAdapter((ArrayList<Demande>) friendList, SearchPeoplesActivity.this, R.layout.horizontal_demande_row);
                    pendingRequest.setAdapter(adapter);
                }
                ProgressDialogAlternative.Dissmiss();
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("Erreur lors de l'envoie", t.getMessage());
                ProgressDialogAlternative.Dissmiss();
                Snackbar.make(SearchPeoplesActivity.this.searchLayout, "Unable To reach server", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", view -> {
                            checkInvitation();
                        }).show();


            }
        });
    }
}
