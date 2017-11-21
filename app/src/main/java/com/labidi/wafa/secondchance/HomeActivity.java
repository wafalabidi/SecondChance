package com.labidi.wafa.secondchance;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.labidi.wafa.secondchance.Fragment.HomeFragment;
import com.labidi.wafa.secondchance.Fragment.ProfileFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;
    final Fragment[] fragments = {new HomeFragment() , new ProfileFragment() } ;
    final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        InitBottomNavigationView();
        this.CommitFragment(new HomeFragment());

    }

    public void CommitFragment(Fragment fragment) {
        if (!isFinishing() && !isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.flContainer, fragment).commitAllowingStateLoss();
        }
    }


    public void InitBottomNavigationView() {
        getSupportActionBar().hide();
        models.add(new NavigationTabBar.Model.Builder(getDrawable(R.drawable.ic_home),
                Color.BLUE).title("Home").badgeTitle("Home").build());
        models.add(new NavigationTabBar.Model.Builder(getDrawable(R.drawable.ic_profil),
                Color.GRAY).title("Profile").badgeTitle("Profile").build());
        models.add(new NavigationTabBar.Model.Builder(getDrawable(R.drawable.ic_notification),
                Color.DKGRAY).title("Notification ").badgeTitle("Notification").build());
        models.add(new NavigationTabBar.Model.Builder(getDrawable(R.drawable.ic_message),
                R.drawable.gradient_1).title("Message").badgeTitle("Message").build());
        navigationTabBar.setSelected(true);
        navigationTabBar.setModels(models);
        navigationTabBar.show();
        navigationTabBar.setIsTitled(false);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.TOP);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.LEFT);
        navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(R.drawable.gradient_1);
        navigationTabBar.setBadgeTitleColor(Color.RED);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setBadgeSize(40);
        navigationTabBar.setTitleSize(40);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                switch (index) {
                    case 0:
                        CommitFragment(fragments[0]);
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        CommitFragment(fragments[1]);
                        Toast.makeText(HomeActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this, "Notification ", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(HomeActivity.this, "Message", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }
}
