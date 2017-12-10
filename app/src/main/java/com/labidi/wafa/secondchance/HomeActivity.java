package com.labidi.wafa.secondchance;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Fragment.HomeFragment;
import com.labidi.wafa.secondchance.Fragment.ProfileFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devlight.io.library.ntb.NavigationTabBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener  {
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;
    final Fragment[] fragments = {new HomeFragment(), new ProfileFragment()};
    final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
    FloatingActionButton fabCamera;
    FloatingActionButton fabGallery;


    public FloatingActionButton getFabCamera() {
        return fabCamera;
    }

    public void setFabCamera(FloatingActionButton fabCamera) {
        this.fabCamera = fabCamera;
    }

    public FloatingActionButton getFabGallery() {
        return fabGallery;
    }

    public void setFabGallery(FloatingActionButton fabGallery) {
        this.fabGallery = fabGallery;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);

        InitBottomNavigationView();
        this.CommitFragment(new HomeFragment());
        // InitMenu();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        fabCamera = findViewById(R.id.menu_camera);
        fabGallery = findViewById(R.id.menu_gallery);


    }

    @OnClick(R.id.menu_camera)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCamera.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCamera.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    public void CommitFragment(Fragment fragment) {
        if (!isFinishing() && !isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.flContainer, fragment).commit();
        }
    }




    public void InitBottomNavigationView() {
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
                        break;
                    case 1:
                        CommitFragment(fragments[1]);
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

    public void InitMenu() {
        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_profil));
        addFr.setDrawable(bd);
        MenuObject close = new MenuObject();

        close.setResource(R.drawable.ic_message);

        MenuObject send = new MenuObject("Send message");
        send.setResource(R.drawable.ic_notification);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(send);
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        // set other settings to meet your needs
        ContextMenuDialogFragment mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    @Override
    public void onClick(View v) {

    }

}
