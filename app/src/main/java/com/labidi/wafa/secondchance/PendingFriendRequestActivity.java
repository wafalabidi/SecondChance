package com.labidi.wafa.secondchance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.PendingInvitationAdapter;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Utils.LocalFiles;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 12/12/2017.
 */

public class PendingFriendRequestActivity extends BaseActivity {
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
        getCurrentUserDemands();
    }

    public void getCurrentUserDemands() {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface demands = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE , Context.MODE_PRIVATE) );
        Call<DemandesResponse> call = demands.checkInvitation(localFiles.getInt(LocalFiles.Id));

        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null) {
                    demandes = (ArrayList<Demande>) response.body().getDemandes();
                    PendingInvitationAdapter adapter = new PendingInvitationAdapter(demandes, PendingFriendRequestActivity.this);
                    rvPendingFriendsRequest.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("Invitation erreur ", t.getMessage());
            }
        });
    }
}
