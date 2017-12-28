package com.labidi.wafa.secondchance.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.customfonts.MyEditText;
import com.labidi.wafa.secondchance.customfonts.MyTextView;

import agency.tango.materialintroscreen.SlideFragment;

/**
 * Created by Wafe on 23/12/2017.
 */

public class LocationFragment extends SlideFragment {

    MyEditText et_work;
    MyEditText et_studies;
    MyTextView tv_country;
    RadioButton br_women;
    RadioButton br_man;
    RadioGroup radioGroup;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_location, container, false);
        et_work = (MyEditText) view.findViewById(R.id.et_work);
        et_studies = (MyEditText) view.findViewById(R.id.et_studies);
        tv_country = (MyTextView) view.findViewById(R.id.tv_country);
        br_women = (RadioButton) view.findViewById(R.id.br_women);
        br_man = (RadioButton) view.findViewById(R.id.br_man);
        radioGroup =(RadioGroup)view.findViewById(R.id.rd);
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.color_logo_sign_up;
    }

    @Override
    public int buttonsColor() {
        return R.color.pink1;
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message);
    }



}
