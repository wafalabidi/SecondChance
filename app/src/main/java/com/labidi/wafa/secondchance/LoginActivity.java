package com.labidi.wafa.secondchance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Tools.CustomViewPager;
import com.labidi.wafa.secondchance.Tools.ViewPagerAdapter;
import com.labidi.wafa.secondchance.Utils.LocalFiles;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private static final int LOGIN_FRAGMENT = 0;
    private static final int SIGNUP_FRAGMENT = 1;
    private static final int RESET_PASSWORD_FRAGMENT = 2;
    private CustomViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    AnimationDrawable animationDrawable;
    ImageView relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPagingEnabled(false);
        changeFragment(LOGIN_FRAGMENT);
        //Background animation
        relativeLayout = findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE , Context.MODE_PRIVATE));
        if (localFiles.getInt(LocalFiles.Id) != -1 ){
            Intent intent = new Intent( this , MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Background animation
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Background animation
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }

    private void changeFragment(int fragmentType) {

        switch (fragmentType) {
            case LOGIN_FRAGMENT:
                viewPager.setCurrentItem(LOGIN_FRAGMENT);
                break;
            case SIGNUP_FRAGMENT:
                viewPager.setCurrentItem(SIGNUP_FRAGMENT);
                break;
            case RESET_PASSWORD_FRAGMENT:
                viewPager.setCurrentItem(RESET_PASSWORD_FRAGMENT);
                break;
            default:
                viewPager.setCurrentItem(LOGIN_FRAGMENT);
                break;
        }


    }

    public void signUpClick(View view) {
        changeFragment(SIGNUP_FRAGMENT);
    }

    public void signInClick(View view) {
        changeFragment(LOGIN_FRAGMENT);
    }

    public void resetPasswordClick(View view) {
        changeFragment(RESET_PASSWORD_FRAGMENT);
    }

    public void backClick(View view){
        changeFragment(LOGIN_FRAGMENT);
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == LOGIN_FRAGMENT)
            super.onBackPressed();
        else {
            changeFragment(LOGIN_FRAGMENT);
        }
    }

    public void logInButtonClicked() {
        Toast.makeText(this, R.string.login_button_click, Toast.LENGTH_SHORT).show();
    }

    public void signUpButtonClicked() {
        Toast.makeText(this, R.string.signup_button_click, Toast.LENGTH_SHORT).show();
    }

    public void resetPasswordButtonClicked() {
        Toast.makeText(this, R.string.reset_password_button_clicked, Toast.LENGTH_SHORT).show();
    }
}

