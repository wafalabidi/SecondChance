package com.esprit.secondchance.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.LoginBody;
import com.esprit.secondchance.Entities.Response.LoginResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.FirstLoginActivity;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Tools.MyTimeUtils;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.customfonts.MyEditText;
import com.esprit.secondchance.customfonts.MyTextView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    EditText firstname_edittext;
    EditText lastname_edittext;
    EditText email_edittext;
    EditText password_edittext;
    TextView birthdate_edittext;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ///Cast
        firstname_edittext =(EditText)getView().findViewById(R.id.firstname_edittext);
        lastname_edittext = (EditText)getView().findViewById(R.id.username_edittext);
        email_edittext = (EditText)getView().findViewById(R.id.email_edittext);
        password_edittext =(EditText) getView().findViewById(R.id.password_edittext);
        birthdate_edittext =(TextView) getView().findViewById(R.id.birthday_edittext);


        getView().findViewById(R.id.clickable_birthday_layout).setOnClickListener(view12 -> birthDayClick());
        getView().findViewById(R.id.signup_button).setOnClickListener(view1 -> {
            boolean ok = true;
            if (((MyEditText) getView().findViewById(R.id.username_edittext)).getText().length() == 0) {
                ((MyEditText) getView().findViewById(R.id.username_edittext)).setError(getString(R.string.empty));
                ok = false;
            }
            if (((MyEditText) getView().findViewById(R.id.password_edittext)).getText().length() == 0) {
                ((MyEditText) getView().findViewById(R.id.password_edittext)).setError(getString(R.string.empty));
                ok = false;
            }
            if (((MyEditText) getView().findViewById(R.id.email_edittext)).getText().length() == 0) {
                ((MyEditText) getView().findViewById(R.id.email_edittext)).setError(getString(R.string.empty));
                ok = false;
            }

            if (ok){
                signUp();
            }
            else {
            Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();}

        });
    }

    public void birthDayClick() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                Calendar temp = Calendar.getInstance();
                temp.set(Calendar.YEAR, year);
                temp.set(Calendar.MONTH, monthOfYear);
                temp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((MyTextView) getView().findViewById(R.id.birthday_edittext)).setText(MyTimeUtils.formatDate(temp.getTimeInMillis(), MyTimeUtils.BIRTHDAY_FORMAT));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();

    }
    private void signUp(){

        final User user = new User();
        user.setFirstName(firstname_edittext.getText().toString());
        user.setLastName(lastname_edittext.getText().toString());
        user.setMail(email_edittext.getText().toString());
        user.setPassword(password_edittext.getText().toString());
        user.setBirthDate(birthdate_edittext.getText().toString());
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.studentLogin(user);

        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Log.e("sss","sss");
                if (response.isSuccessful()) {
                    login();
                }
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("failed to register");
                dialog.create().show();

            }
        });

    }
    private void login(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Processing ...");
        progressDialog.show();
        LoginBody user = new LoginBody();
        user.setMail(email_edittext.getText().toString());
        user.setPassword(password_edittext.getText().toString());
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.LoginInterface loginInterface = retrofitClient.getRetrofit().create(UserService.LoginInterface.class);
        Call<LoginResponse> call = loginInterface.userLogin(user);
        call.enqueue(new Callback<LoginResponse>() {
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE,Context.MODE_PRIVATE));
                    User user1 = response.body().getUser();
                    user1.setImg_profile(retrofitClient.BASE_URL+user1.getImg_profile());
                    user1.setImg_couverture(retrofitClient.BASE_URL+user1.getImg_couverture());
                    localFiles.logIn(user1);
                    progressDialog.dismiss();
                    Intent intent = new Intent(getActivity(), FirstLoginActivity.class);
                    startActivity(intent);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

}
