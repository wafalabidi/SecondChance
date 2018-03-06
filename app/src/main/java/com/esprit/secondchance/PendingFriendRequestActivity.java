package com.esprit.secondchance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.PendingInvitationAdapter;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Utils.LocalFiles;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 12/12/2017.
 */

public class PendingFriendRequestActivity extends BaseDrawerActivity {
    ArrayList<Demande> demandes;
    @BindView(R.id.rvPendingFriendsRequest)
    RecyclerView rvPendingFriendsRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_friends_request);
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPendingFriendsRequest.setLayoutManager(layoutManager);
        if (BaseDrawerActivity.demandes.size() != 0) {
            setUpRecycler(BaseDrawerActivity.demandes);
        }else{
            getCurrentUserDemands();
        }


        getCurrentUserDemands();
    }

    public void getCurrentUserDemands() {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface demands = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = demands.checkInvitation(localFiles.getInt(LocalFiles.Id));

        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null && response.body().getDemandes().size() > 0) {
                    setUpRecycler((ArrayList<Demande>) response.body().getDemandes());

                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("Invitation erreur ", t.getMessage());
            }
        });
    }

    private void setUpRecycler(ArrayList<Demande> list) {
        demandes = (ArrayList<Demande>) list;
        PendingInvitationAdapter adapter = new PendingInvitationAdapter(demandes, PendingFriendRequestActivity.this, R.layout.pending_friend_request_row);
        rvPendingFriendsRequest.setAdapter(adapter);
    }
}
