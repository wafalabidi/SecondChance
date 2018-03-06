package com.esprit.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.Demande;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.NonSwipeableViewPager;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wafa on 11/12/2017.
 */

public class PrivateProfileFragment extends Fragment {
    TextView tv_user_firstname;
    TextView tv_work;
    CircleImageView cim_img_profile;
    User currentUser;
    KenBurnsView couverture_pic;
    NonSwipeableViewPager vpProfil;
    Button bFollowProfil;
    private PictureListFragment pictureListFragment;
    private LocalFiles localFiles;
    private ArrayList<Demande> friendList;
    private InfoProfileFragment infoProfileFragment ;
    TabLayout tabLayout;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void Init() {
        tv_user_firstname.setText(currentUser.getFirstName());
        tv_work.setText(currentUser.getLastName());
        Log.e("current SUer", currentUser.getImg_couverture());
        Log.e("current SUer", currentUser.getImg_profile());
        Picasso.with(getActivity()).load(currentUser.getImg_profile()).placeholder(R.drawable.couple).into(cim_img_profile);
        Picasso.with(getActivity()).load(currentUser.getImg_couverture()).placeholder(R.drawable.couverture).into(couverture_pic);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        couverture_pic = view.findViewById(R.id.couverture_pic);
        tv_user_firstname = view.findViewById(R.id.tv_user_firstName);
        tv_work = view.findViewById(R.id.tv_work);
        cim_img_profile = view.findViewById(R.id.avatar);
        vpProfil = view.findViewById(R.id.vpProfil);
        bFollowProfil = view.findViewById(R.id.bFollowProfil);
        tabLayout = view.findViewById(R.id.tabs);
        bFollowProfil.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();
        });
        Init();
        InitPager();
        checkInvitation();
        return view;
    }

    public void InitPager() {
        infoProfileFragment = new InfoProfileFragment() ;
        infoProfileFragment.setUser(currentUser);
        pictureListFragment = new PictureListFragment();
        pictureListFragment.setCurrentUser(currentUser);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(pictureListFragment, "Posts");
        adapter.addFragment(infoProfileFragment, "Infos");
        vpProfil.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpProfil);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void checkInvitation() {
        ProgressDialogAlternative.ShowDIalog(getActivity());
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = registerInterface.checkFriendShip(localFiles.getInt(LocalFiles.Id), currentUser.getId());
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                friendList = (ArrayList<Demande>) response.body().getDemandes();
                ProgressDialogAlternative.Dissmiss();
                if (friendList != null) {
                    if (friendList.get(0).getState() == 0) {
                        if (friendList.get(0).getIdUser2() == localFiles.getInt(LocalFiles.Id)) {
                            bFollowProfil.setText("Accept Invitation");
                            bFollowProfil.setOnClickListener(v -> acceptInvitaiton());
                        } else if (friendList.get(0).getIdUser() == localFiles.getInt(LocalFiles.Id)) {
                            bFollowProfil.setText("Invtation sent");
                            bFollowProfil.setEnabled(false);

                        }
                    } else {
                        bFollowProfil.setVisibility(View.GONE);
                    }

                } else {
                    bFollowProfil.setOnClickListener(view -> {
                        sendInvitaion();

                    });
                }
                ProgressDialogAlternative.ShowDIalog(getActivity());

            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                ProgressDialogAlternative.Dissmiss();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    public void sendInvitaion() {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.sendRequest(localFiles.getInt(LocalFiles.Id), currentUser.getId());
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Toast.makeText(getActivity(), "Invitation Sent", Toast.LENGTH_SHORT).show();
                bFollowProfil.setEnabled(false);
                bFollowProfil.setText("Invtation sent");

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to send Initation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void acceptInvitaiton() {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.accepterDemande(currentUser.getId(), localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Toast.makeText(getActivity(), "Invitation Accepted", Toast.LENGTH_SHORT).show();
                bFollowProfil.setText("Add");

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please retry later", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

