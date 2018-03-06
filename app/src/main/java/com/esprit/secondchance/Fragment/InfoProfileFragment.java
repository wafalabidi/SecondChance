package com.esprit.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 18/01/2018.
 */

public class InfoProfileFragment extends Fragment {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.last_name)
    EditText last_name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.work)
    EditText work;
    @BindView(R.id.studies)
    EditText studies;
    @BindView(R.id.country)
    EditText country;
    @BindView(R.id.city)
    EditText city;
    private User user;
    LocalFiles localFiles;
    @BindView(R.id.updateButton)
    Button updateButton;

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        ButterKnife.bind(this, view);
        if (user != null) {
            InitiUser();
        } else {
            InitUserFromLocalFiles();
        }


        return view;
    }

    private void InitiUser() {

        username.setText(user.getFirstName());
        username.setEnabled(false);
        last_name.setText(user.getLastName());
        last_name.setEnabled(false);
        email.setVisibility(View.GONE);
        work.setText(user.getWork());
        work.setEnabled(false);
        description.setText(user.getHobbies());
        description.setEnabled(false);
        studies.setText(user.getStudies());
        studies.setEnabled(false);
        country.setEnabled(false);
        country.setText(user.getCountry());
        updateButton.setVisibility(View.GONE);

    }

    private void InitUserFromLocalFiles() {
        localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        username.setText(localFiles.getString(LocalFiles.FirstName));
        last_name.setText(localFiles.getString(LocalFiles.LastName));
        email.setText(localFiles.getString(LocalFiles.Mail));
        work.setText(localFiles.getString(localFiles.Work));
        description.setText(localFiles.getString(LocalFiles.Hobbies));
        studies.setText(localFiles.getString(LocalFiles.Studies));
        country.setText(localFiles.getString(LocalFiles.Country));

        updateButton.setOnClickListener(v -> updateUser());
    }

    private void updateUser() {
        User user = new User();
        user.setId(localFiles.getInt(LocalFiles.Id));
        user.setFirstName(username.getText().toString());
        user.setLastName(last_name.getText().toString());
        user.setMail(email.getText().toString());
        user.setWork(work.getText().toString());
        user.setHobbies(description.getText().toString());
        user.setStudies(studies.getText().toString());
        user.setCountry(country.getText().toString());
        user.setImg_couverture(localFiles.getString(LocalFiles.imgcouverture));
        user.setImg_profile(localFiles.getString(LocalFiles.imgprofile));
        user.setPassword(localFiles.getString(LocalFiles.Password));

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.updateInfoInterface updateInfoInterface = retrofitClient.getRetrofit().create(UserService.updateInfoInterface.class);
        Call<ConfirmationResponse> call = updateInfoInterface.userUpdateInfo(user);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                LocalFiles.LogOut(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
                localFiles.logIn(user);
                Toast.makeText(getActivity(), "Information updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to acces remote server , please retry later", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
