package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sofien on 14/11/2017.
 */

public class OtherFragment extends Fragment {
    @BindView(R.id.alchoolEveryDay)
    RadioButton alchoolEveryday;
    @BindView(R.id.alchoolNever)
    RadioButton alchoolNever;
    @BindView(R.id.alchoolOccasionaly)
    RadioButton alchoolOccasionaly;
    @BindView(R.id.alchoolOften)
    RadioButton alchoolOften;

    @BindView(R.id.druglEveryDay)
    RadioButton druglEveryDay;
    @BindView(R.id.drugNever)
    RadioButton drugNever;
    @BindView(R.id.drugOccasionaly)
    RadioButton drugOccasionaly;
    @BindView(R.id.drugOften)
    RadioButton drugOften;

    @BindView(R.id.tobacoEveryDay)
    RadioButton tobacoEveryDay;
    @BindView(R.id.tobacoNever)
    RadioButton tobacoNever;
    @BindView(R.id.tobacoOccasionaly)
    RadioButton tobacoOccasionaly;
    @BindView(R.id.tobacoOften)
    RadioButton tobacoOften;

    @BindView(R.id.bFinish)
    Button bFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = inflater.inflate(R.layout.other_fragment, null);
        ButterKnife.bind(this ,view);
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTobaco();
                checkDrug();
                checkAlchool();
                Log.e("user", ((FirstLoginActivity) getActivity()).getUser().toString());
                ((FirstLoginActivity) getActivity()).UpdateInfo();
            }
        });
        return view;
    }

    private void checkAlchool() {
        if (alchoolEveryday.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setAlchool("Everyday");
        } else if (alchoolNever.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setAlchool("Never");

        } else if (alchoolOccasionaly.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setAlchool("Occasionaly");

        } else if (alchoolOften.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setAlchool("Often");

        }
    }

    private void checkTobaco() {
        if (tobacoEveryDay.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setTobaco("Everyday");

        } else if (tobacoNever.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setTobaco("Never");

        } else if (tobacoOccasionaly.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setTobaco("Occasionaly");

        } else if (tobacoNever.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setTobaco("Never");

        }
    }

    private void checkDrug() {
        if (druglEveryDay.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setDrug("Everyday");
        } else if (drugOccasionaly.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setDrug("Occasionaly");
        } else if (drugNever.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setDrug("Never");
        } else if (drugOften.isChecked()) {
            ((FirstLoginActivity) getActivity()).getUser().setDrug("Often");

        }
    }
}
