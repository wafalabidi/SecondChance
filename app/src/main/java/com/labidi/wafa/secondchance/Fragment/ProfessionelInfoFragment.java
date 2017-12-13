package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sofien on 13/11/2017.
 */

public class ProfessionelInfoFragment extends Fragment {
   @BindView(R.id.etWork)
   EditText etWork ;
   @BindView(R.id.etHobbies)
   EditText etHobbies ;
   @BindView(R.id.etStudies)
   EditText etStudies ;
   @BindView(R.id.bNext)
   Button bNext ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(inflater == null){
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        }
        View view = inflater.inflate(R.layout.professionel_info_fragment, null ) ;

        ButterKnife.bind(this ,view);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FirstLoginActivity) getActivity()) .getUser().setWork(etWork.getText().toString());
                ((FirstLoginActivity) getActivity()) .getUser().setHobbies(etHobbies.getText().toString());
                ((FirstLoginActivity) getActivity()).getUser().setStudies(etStudies.getText().toString());
                ((FirstLoginActivity) getActivity()).getViewPager().setCurrentItem(2);

            }
        });
        return view ;

    }
}
