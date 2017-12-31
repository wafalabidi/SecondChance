package com.labidi.wafa.secondchance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Fragment.PictureListFragment;
import com.labidi.wafa.secondchance.Fragment.PrivateProfileFragment;
import com.labidi.wafa.secondchance.Fragment.ProfileFragment;
import com.labidi.wafa.secondchance.Fragment.ViewPostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.labidi.wafa.secondchance.TakePhotoActivity.ARG_REVEAL_START_LOCATION;

/**
 * Created by Wafa on 11/12/2017.
 */

public class UserProfileActivity extends BaseDrawerActivity {



    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity,int idUser) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        int idUser = getIntent().getIntExtra("idUser", 0);
        if (idUser == 0 || idUser == User.Id) {
            new ProfileFragment();
        } else {
            getUser(idUser);
        }
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.UserProfileLayout, profileFragment,profileFragment.getTag())
                .commit();
            PictureListFragment pictureListFragment= new PictureListFragment();
            manager.beginTransaction().replace(R.id.PostLayout, pictureListFragment,pictureListFragment.getTag())
                    .commit();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }


    public void getUser(int idUser) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface service = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<LoginResponse> call = service.getUserById(idUser);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getUser() != null) {
                    User user = response.body().getUser();
                    PrivateProfileFragment privateProfileFragment = new PrivateProfileFragment();
                    privateProfileFragment.setCurrentUser(user);
                    commitFragment(privateProfileFragment);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

public  void commitFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container,f);
}
}
