package com.labidi.wafa.secondchance.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.HomeActivity;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.customfonts.MyEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    EditText email_edittext;
    EditText password_edittext;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Cast
        email_edittext = (EditText) getView().findViewById(R.id.email_edittext);
        password_edittext = (EditText) getView().findViewById(R.id.password_edittext);

        getView().findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok = true;
                if (((MyEditText) getView().findViewById(R.id.email_edittext)).getText().length() == 0) {
                    ((MyEditText) getView().findViewById(R.id.email_edittext)).setError(getString(R.string.empty));
                    ok = false;
                }
                if (((MyEditText) getView().findViewById(R.id.password_edittext)).getText().length() == 0) {
                    ((MyEditText) getView().findViewById(R.id.password_edittext)).setError(getString(R.string.empty));
                    ok = false;
                }
                if (ok){
                    Log.e("hello","fama mochkla");
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Processing ...");
                    progressDialog.show();
                    InscriptionBody user = new InscriptionBody() ;
                    user.setMail(email_edittext.getText().toString());
                    user.setPassword(password_edittext.getText().toString());
                    RetrofitClient retrofitClient = new RetrofitClient() ;
                    UserService.LoginInterface loginInterface = retrofitClient.getRetrofit().create(UserService.LoginInterface.class) ;
                    Call<LoginResponse> call = loginInterface.userLogin(user);
                    call.enqueue(new Callback<LoginResponse>() {
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Log.e("Ok" , response.body().getUser().toString());
                            progressDialog.dismiss();
                            Intent intent = new Intent( getActivity(),  HomeActivity.class) ;
                            startActivity(intent);

                        }

                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                        }
                    });

                }

            }

        });

    }
}
