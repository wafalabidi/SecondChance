package com.esprit.secondchance.Fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.LoginBody;
import com.esprit.secondchance.Entities.Response.LoginResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.MainActivity;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;
import com.esprit.secondchance.customfonts.MyEditText;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

   // @BindView(R.id.google_sign_in_button)
    //SignInButton google_sign_in_button;
    EditText email_edittext;
    EditText password_edittext;
    CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private final int RC_SIGN_IN = 101;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        //SetupGoogleLogin();
        /*google_sign_in_button.setOnClickListener(e -> {
            SigUpGoogle();
        });*/
        return view;
    }


    private void SigUpGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void SetupGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().
                build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            ChangeActivity();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Cast
        email_edittext = getView().findViewById(R.id.email_edittext);
        password_edittext = getView().findViewById(R.id.password_edittext);

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

                ProgressDialogAlternative.ShowDIalog(getActivity());
                LoginBody user = new LoginBody();
                user.setMail(email_edittext.getText().toString());
                user.setPassword(password_edittext.getText().toString());
                RetrofitClient retrofitClient = new RetrofitClient();
                UserService.LoginInterface loginInterface = retrofitClient.getRetrofit().create(UserService.LoginInterface.class);
                Call<LoginResponse> call = loginInterface.userLogin(user);
                call.enqueue(new Callback<LoginResponse>() {
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
                            User user1 = response.body().getUser();
                            user1.setImg_profile(RetrofitClient.BASE_URL + user1.getImg_profile());
                            user1.setImg_couverture(RetrofitClient.BASE_URL + user1.getImg_couverture());
                            localFiles.logIn(user1);
                            ChangeActivity();
                        } else {
                            Toast.makeText(getActivity(), "Invalid creditential", Toast.LENGTH_SHORT).show();
                        }
                        ProgressDialogAlternative.Dissmiss();

                    }

                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),
                                t.getMessage(), Toast.LENGTH_SHORT).show();
                        ProgressDialogAlternative.Dissmiss();
                    }
                });

            }

        });

    }

    public void ChangeActivity() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.e("account " , account.toString() );
        } catch (ApiException e) {
            Log.e("Google Sign In failur", "signInResult:failed code=" + e.getMessage());
        }

    }

}
