package com.labidi.wafa.secondchance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Fragment.InformationFragment;
import com.labidi.wafa.secondchance.Fragment.OtherFragment;
import com.labidi.wafa.secondchance.Fragment.ProfessionelInfoFragment;
import com.labidi.wafa.secondchance.Tools.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstLoginActivity extends AppCompatActivity {
    User user;
    @BindView(R.id.vpFirstLogin)
    ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        ButterKnife.bind(this);
        Bundle extra = getIntent().getExtras();

        user = new User();
        user.setPassword(extra.getString("mail"));
        user.setMail(extra.getString("password"));
        user.setFirstName(extra.getString("firstName"));
        user.setLastName(extra.getString("lastName"));
        PagerAdapter pagerAdapter = new PagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true , new ForegroundToBackgroundTransformer());
    }

    private class PagerAdaper extends FragmentPagerAdapter {

        public PagerAdaper(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new InformationFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new ProfessionelInfoFragment();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return new OtherFragment();
                default:
                    return null;
            }
        }


    }


    public void UpdateInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing ...");
        progressDialog.show();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.updateInfoInterface updateInfoInterface = retrofitClient.getRetrofit().create(UserService.updateInfoInterface.class);
        Call<ConfirmationResponse> call = updateInfoInterface.userUpdateInfo(user);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FirstLoginActivity.this, "all good ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FirstLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
}
