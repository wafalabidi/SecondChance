package com.esprit.secondchance.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.secondchance.R;
import com.esprit.secondchance.customfonts.MyEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.reset_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok = true;
                if (((MyEditText) getView().findViewById(R.id.email_edittext)).getText().length() == 0) {
                    ((MyEditText) getView().findViewById(R.id.email_edittext)).setError(getString(R.string.empty));
                    ok = false;
                }
            }
        });
    }
}
