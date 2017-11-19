package com.labidi.wafa.secondchance;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.labidi.wafa.secondchance.Fragment.LoginFragment;
import com.labidi.wafa.secondchance.Fragment.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        button = (Button) findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommitFragment(  new SignUpFragment(),new LoginFragment());
            }
        });

    }

    public void CommitFragment(Fragment fragment1 , Fragment fragment2) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        FragmentTransactionExtended fragmentTransactionExtended =
                new FragmentTransactionExtended(this, fragmentTransaction,
                        fragment1, fragment2, R.id.container);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.FLIP_VERTICAL);
        fragmentTransactionExtended.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
}