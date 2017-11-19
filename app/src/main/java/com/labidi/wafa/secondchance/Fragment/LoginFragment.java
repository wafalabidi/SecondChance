package com.labidi.wafa.secondchance.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.LoginActivity;
import com.labidi.wafa.secondchance.MainActivity;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 19/11/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.et_login)
    EditText et_login;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_signup)
    TextView tv_signup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(inflater == null){
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        View view= inflater.inflate(R.layout.login_fragment , null) ;
        ButterKnife.bind(this,view);
        btn_login = (Button)view.findViewById(R.id.btn_login);
        tv_signup = (TextView) view.findViewById(R.id.tv_signup);
        et_login = (EditText) view.findViewById(R.id.et_login);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_login.setOnClickListener(this);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).CommitFragment(new LoginFragment(),new SignUpFragment() );
            }
        });
        if (getActivity().getIntent().getExtras() != null){
            Bundle bundle = getActivity().getIntent().getExtras();
            et_login.setText(bundle.getString("password"));
            et_password.setText(bundle.getString("mail"));
        }
        return view;

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            Log.e("hello","fama mochkla");
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Processing ...");
            progressDialog.show();
            InscriptionBody user = new InscriptionBody() ;
            user.setMail(et_login.getText().toString());
            user.setPassword(et_password.getText().toString());
            RetrofitClient retrofitClient = new RetrofitClient() ;
            UserService.LoginInterface loginInterface = retrofitClient.getRetrofit().create(UserService.LoginInterface.class) ;
            Call<LoginResponse> call = loginInterface.userLogin(user);
            call.enqueue(new Callback<LoginResponse>() {
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.e("Ok" , response.body().getUser().toString());
                    progressDialog.dismiss();
                    Intent intent = new Intent( getActivity(),  LoginActivity.class) ;
                    startActivity(intent);

                }

                public void onFailure(Call<LoginResponse> call, Throwable t) {
                }
            });

        }else if (v.getId() == R.id.tv_signup){
            Intent intent = new Intent(getActivity(), SignUpFragment.class) ;
            startActivity(intent);
        }
    }
}
