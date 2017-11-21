package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.labidi.wafa.secondchance.Adapters.CustomPagerAdapter;
import com.labidi.wafa.secondchance.R;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by sofien on 20/11/2017.
 */

public class ProfileFragment extends Fragment {
    NavigationTabBar navigationTabBar;
    ArrayList<NavigationTabBar.Model> models;
    ViewPager viewPager;
    final Fragment[] fragments = {new HomeFragment(), new FriendsListFragments()};
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        view = inflater.inflate(R.layout.profile_fragment, null);
        init(view);
        return view;
    }

    void init(View view) {
        navigationTabBar = (NavigationTabBar) view.findViewById(R.id.ntbProfil);
        models = new ArrayList<>();
        viewPager = (ViewPager) view.findViewById(R.id.vpProfil);
        CustomPagerAdapter adapter = new CustomPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        InitBottomNavigationView();

    }

    @Override
    public void onResume() {
        super.onResume();
        init(view);
    }

    public void InitBottomNavigationView() {
        models.add(new NavigationTabBar.Model.Builder(getActivity().getDrawable(R.drawable.ic_home),
                Color.BLUE).title("Home").badgeTitle("Home").build());
        models.add(new NavigationTabBar.Model.Builder(getActivity().getDrawable(R.drawable.ic_profil),
                Color.GRAY).title("Profile").badgeTitle("Profile").build());
        models.add(new NavigationTabBar.Model.Builder(getActivity().getDrawable(R.drawable.ic_notification),
                Color.DKGRAY).title("Notification ").badgeTitle("Notification").build());
        models.add(new NavigationTabBar.Model.Builder(getActivity().getDrawable(R.drawable.ic_message),
                R.drawable.gradient_1).title("Message").badgeTitle("Message").build());
        navigationTabBar.setSelected(true);
        navigationTabBar.setModels(models);
        navigationTabBar.show();
        navigationTabBar.setIsTitled(false);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.TOP);
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(R.drawable.gradient_1);
        navigationTabBar.setBadgeTitleColor(Color.RED);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        //navigationTabBar.setViewPager(viewPager);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:

                        Toast.makeText(getActivity(), "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Notification ", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Message", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }
}
