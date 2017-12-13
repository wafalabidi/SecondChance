package com.labidi.wafa.secondchance.Fragment;


import android.app.DatePickerDialog;
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

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.TestActivity;
import com.labidi.wafa.secondchance.Tools.MyTimeUtils;
import com.labidi.wafa.secondchance.customfonts.MyEditText;
import com.labidi.wafa.secondchance.customfonts.MyTextView;

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


        getView().findViewById(R.id.clickable_birthday_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayClick();
            }
        });
        getView().findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Log.e("hello","fama mochkla");

                if (ok){
                    final InscriptionBody user = new InscriptionBody();
                    user.setFirstName(firstname_edittext.getText().toString());
                    user.setLastName(lastname_edittext.getText().toString());
                    user.setMail(email_edittext.getText().toString());
                    user.setPassword(password_edittext.getText().toString());
                    user.setBirthdate(birthdate_edittext.getText().toString());
                    RetrofitClient retrofitClient = new RetrofitClient();
                    UserService.RegisterInterface registerInterface = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
                    Call<ConfirmationResponse> call = registerInterface.studentLogin(user);
                    call.enqueue(new Callback<ConfirmationResponse>() {
                        @Override
                        public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getActivity() , FirstLoginActivity.class);
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
            }
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

}
