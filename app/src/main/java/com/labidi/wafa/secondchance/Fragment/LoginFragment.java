package com.labidi.wafa.secondchance.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.LoginBody;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.HomeActivity;
import com.labidi.wafa.secondchance.MainActivity;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.Utils.LocalFiles;
import com.labidi.wafa.secondchance.customfonts.MyEditText;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    EditText email_edittext;
    EditText password_edittext;
    LoginButton facebook_login;
    CallbackManager callbackManager;
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
        facebook_login= (LoginButton) getView().findViewById(R.id.facebook_login);
        facebook_login.setReadPermissions("email");
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               String userId= loginResult.getAccessToken().getUserId();
                GraphRequest  graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                    }
                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","firts_name,last_name,email,id");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("hello","ljl");

            }
        });
        getView().findViewById(R.id.login_button).setOnClickListener(view1 -> {
            boolean ok = true;
            if (((MyEditText) getView().findViewById(R.id.email_edittext)).getText().length() == 0) {
                ((MyEditText) getView().findViewById(R.id.email_edittext)).setError(getString(R.string.empty));
                ok = false;
            }
            if (((MyEditText) getView().findViewById(R.id.password_edittext)).getText().length() == 0) {
                ((MyEditText) getView().findViewById(R.id.password_edittext)).setError(getString(R.string.empty));
                ok = false;
            }
            if (ok) {
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
                            localFiles.logIn(user1);
                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Invalid creditential", Toast.LENGTH_SHORT).show();
                        }

                    }

                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),
                                t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }

        });

    }


    public void displayUserInfo(JSONObject object){
        String first_name, last_name , email ,id;
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final InscriptionBody user = new InscriptionBody();
        //user.setFirstName(first_name.getText().toString());
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<ConfirmationResponse> call = registerInterface.studentLogin(user);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getActivity() , MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("firstName",user.getFirstName());
                    bundle.putString("lastName",user.getLastName());
                    bundle.putString("mail", user.getMail());
                    bundle.putString("password", user.getPassword());
                    bundle.putString("birthdate",user.getBirthdate());
                    intent.putExtras(bundle);

                    startActivity(intent);


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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
