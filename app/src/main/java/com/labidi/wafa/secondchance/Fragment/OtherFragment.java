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


}
