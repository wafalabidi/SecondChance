package com.labidi.wafa.secondchance.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.labidi.wafa.secondchance.R;

/**
 * Created by Wafee on 10/01/2018.
 */

public class EditProfileFragment extends Fragment {

    EditText username;
    EditText last_name;
    EditText email;
    EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editprofile, container, false);
    }


}
