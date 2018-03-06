package com.esprit.secondchance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.Response.LoginResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.Fragment.PrivateProfileFragment;
import com.esprit.secondchance.Fragment.ProfileFragment;
import com.esprit.secondchance.Utils.LocalFiles;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wafa on 11/12/2017.
 */

public class UserProfileActivity extends BaseDrawerActivity {
    private boolean pendingIntroAnimation = true;

    private static final int ANIM_DURATION_TOOLBAR = 300;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        int idUser = getIntent().getIntExtra("idUser", 0);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        if (idUser == 0 || idUser == localFiles.getInt(LocalFiles.Id)) {
            commitFragment(new ProfileFragment());
        } else {
            getUser(idUser);

        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void getUser(int idUser) {


        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface service = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<LoginResponse> call = service.getUserById(idUser);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getUser() != null) {
                    User user = response.body().getUser();
                    String imgProfil = RetrofitClient.BASE_URL + user.getImg_profile();
                    user.setImg_profile(imgProfil);

                    String imgCouverture = RetrofitClient.BASE_URL + user.getImg_couverture();
                    user.setImg_couverture(imgCouverture);
                    PrivateProfileFragment privateProfileFragment = new PrivateProfileFragment();
                    privateProfileFragment.setCurrentUser(user);
                    commitFragment(privateProfileFragment);

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            }
        });
    }

    public void commitFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {

        int actionbarSize = Util.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)

                .start();

    }

    public void ResearchClicked(View view) {
        Intent intent = new Intent(this, SearchPeoplesActivity.class);
        startActivity(intent);
    }


}
