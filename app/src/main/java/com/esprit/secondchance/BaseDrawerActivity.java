package com.esprit.secondchance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.PendingInvitationAdapter;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.Utils.LocalFiles;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wafa on 09/12/2017.
 */

public class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;
    public static ArrayList<Demande> demandes = new ArrayList<>();

    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    private CircleImageView ivMenuUserProfilePhoto;
//    private ImageView coverPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();
    }


    @Override
    protected void setupToolbar() {

        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        }
    }

    private void setupHeader() {
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));

        vNavigation.setNavigationItemSelectedListener(this);
        View headerView = vNavigation.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tvNameDrawer);

        if (!TextUtils.isEmpty(localFiles.getString(LocalFiles.FirstName))) {
            String name = localFiles.getString(LocalFiles.FirstName) + "  " + localFiles.getString(LocalFiles.LastName);
            tvName.setText(name);
        }

/*        ImageButton ibMenuLogOut = headerView.findViewById(R.id.ibMenuLogOut);
        ibMenuLogOut.setOnClickListener(v -> {
            LocalFiles.LogOut(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
            Intent intent = new Intent(BaseDrawerActivity.this, LoginActivity.class);
            startActivity(intent);
        });
*/
        ivMenuUserProfilePhoto = headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        //coverPic = headerView.findViewById(R.id.coverPic);
        ivMenuUserProfilePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        });

        if (!TextUtils.isEmpty(localFiles.getString(LocalFiles.imgprofile))) {
            Picasso.with(this).load(localFiles.getString(LocalFiles.imgprofile)).placeholder(R.drawable.couple1).into(ivMenuUserProfilePhoto);

        }
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(v -> onGlobalMenuHeaderClick(v));
        getCurrentUserDemands();
     /*  if (localFiles.getString(LocalFiles.imgcouverture) != "") {
            Picasso.with(this).load(localFiles.getString(LocalFiles.imgcouverture)).into(coverPic);
        } else {
            coverPic.setImageResource(R.drawable.couple);
        }
*/
    }

    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);

        new Handler().postDelayed(() -> {
            int[] startingLocation = new int[2];
            v.getLocationOnScreen(startingLocation);
            startingLocation[0] += v.getWidth() / 2;
            //ProfileFragment.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
            overridePendingTransition(0, 0);
        }, 200);
    }

    public void getCurrentUserDemands() {
        RetrofitClient retrofitClient = new RetrofitClient();
        final UserService.RegisterInterface demands = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = demands.checkInvitation(localFiles.getInt(LocalFiles.Id));

        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null && response.body().getDemandes().size() > 0) {
                    demandes = (ArrayList<Demande>) response.body().getDemandes();
                    String string = "" + demandes.size();
                    TextView textView = (TextView) vNavigation.getMenu().findItem(R.id.menu_pending_request).getActionView();
                    textView.setText(string);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    //textView.setPadding(0,55,0,0);
                    textView.setTextColor(getResources().getColor(R.color.darkRed));

                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("Invitation erreur ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search_peoples) {
            Intent intent = new Intent(this, SearchPeoplesActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.menu_pending_request) {
            Intent intent = new Intent(this, PendingFriendRequestActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_friends) {
            Intent intent = new Intent(this, FriendsListActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_log_out) {
            LocalFiles.LogOut(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
