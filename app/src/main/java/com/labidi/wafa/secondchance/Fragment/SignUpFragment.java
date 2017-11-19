package com.labidi.wafa.secondchance.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.LoginActivity;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 19/11/2017.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.et_firstname)
    EditText et_firstname;
    @BindView(R.id.et_lastname)
    EditText et_lastname;
    @BindView(R.id.et_mail)
    EditText et_mail;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confimPassword)
    EditText et_confimPassword;
    @BindView(R.id.btn_signup)
    Button btn_signup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(inflater == null){
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        View view= inflater.inflate(R.layout.sign_up_fragment , null) ;
        ButterKnife.bind(this,view);
        btn_signup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if ((!TextUtils.isEmpty(et_firstname.getText().toString()))
                && (!TextUtils.isEmpty(et_lastname.getText()))
                && (!TextUtils.isEmpty(et_mail.getText()))
                && (!TextUtils.isEmpty(et_password.getText()))
                && (!TextUtils.isEmpty(et_confimPassword.getText()))

                ) {
            final InscriptionBody user = new InscriptionBody();
            user.setFirstName(et_firstname.getText().toString());
            user.setLastName(et_lastname.getText().toString());
            user.setPassword(et_password.getText().toString());
            user.setMail(et_mail.getText().toString());
            RetrofitClient retrofitClient = new RetrofitClient();
            //SweetAlertDialog pdialog = new SweetAlertDialog(this , SweetAlertDialog.PROGRESS_TYPE);
            //pdialog.setTitleText("Processing Please wait");
            //pdialog.setCancelable(false);
            //pdialog.show();
            UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
            Call<ConfirmationResponse> call = registerInterface.studentLogin(user);
            call.enqueue(new Callback<ConfirmationResponse>() {
                @Override
                public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(getActivity() , LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mail", user.getMail());
                        bundle.putString("password", user.getPassword());
                        intent.putExtras(bundle);
                        //startActivity(intent);


                    }
                }

                @Override
                public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setMessage("failed to register");
                    dialog.create().show();

                }
            });


        } else {
        }
    }
}
