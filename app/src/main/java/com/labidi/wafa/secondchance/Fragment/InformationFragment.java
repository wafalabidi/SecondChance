package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.FirstLoginActivity;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sofien on 13/11/2017.
 */

public class InformationFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.cvBlackEyes)
    CardView cvBlackEyes;
    @BindView(R.id.cvBlueEyes)
    CardView cvBlueEyes;
    @BindView(R.id.cvGreenEyes)
    CardView cvGreenEyes;
    @BindView(R.id.cvGreyEyes)
    CardView cvGreyEyes;
    @BindView(R.id.cvBrownEyes)
    CardView cvBrownEyes;
    @BindView(R.id.ivBlackEyes)
    ImageView ivBlackEyes;
    @BindView(R.id.ivBrownEyes)
    ImageView ivBrownEyes;
    @BindView(R.id.ivBlueEyes)
    ImageView ivBlueEyes;
    @BindView(R.id.ivGreenEyes)
    ImageView ivGrreenEyes;
    @BindView(R.id.ivGreyEyes)
    ImageView ivGreyEyes;


    @BindView(R.id.cvWhiteSkin)
    CardView cvWhiteSkin;
    @BindView(R.id.cvBrownSkin)
    CardView cvBrownSkin;
    @BindView(R.id.cvBlackSkin)
    CardView cvBlackSkin;
    @BindView(R.id.cvYellowSkin)
    CardView cvYellowSkin;
    @BindView(R.id.ivBlackSkin)
    ImageView ivBlackSkin;
    @BindView(R.id.ivWhiteSkin)
    ImageView ivWhiteSkin;
    @BindView(R.id.ivYellowSkin)
    ImageView ivYellowSkin;
    @BindView(R.id.ivBrownSkin)
    ImageView ivBrownSkin;

    @BindView(R.id.etWeight)
    EditText etWeight;
    @BindView(R.id.spBodyShape)
    Spinner spBodyShape;
    @BindView(R.id.etSize)
    EditText etSize;
    @BindView(R.id.bNextProfilFragment)
    Button bNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = inflater.inflate(R.layout.information_fragment, null);
        ButterKnife.bind(this, view);
        ivBlackEyes.setOnClickListener(this);
        ivBrownEyes.setOnClickListener(this);
        ivBlueEyes.setOnClickListener(this);
        ivGreyEyes.setOnClickListener(this);
        ivGrreenEyes.setOnClickListener(this);


        ivBlackSkin.setOnClickListener(this);
        ivWhiteSkin.setOnClickListener(this);
        ivYellowSkin.setOnClickListener(this);
        ivBrownSkin.setOnClickListener(this);

        bNext.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBrownSkin) {
            ((FirstLoginActivity) getActivity()).getUser().setSkinColour("Brown");
            setSelectedSkin(v);
        } else if (v.getId() == R.id.ivBlackSkin) {
            ((FirstLoginActivity) getActivity()).getUser().setSkinColour("Black");
            setSelectedSkin(v);
        } else if (v.getId() == R.id.ivWhiteSkin) {
            ((FirstLoginActivity) getActivity()).getUser().setSkinColour("Whtie");
            setSelectedSkin(v);
        } else if (v.getId() == R.id.ivYellowSkin) {
            ((FirstLoginActivity) getActivity()).getUser().setSkinColour("Yellow");
            setSelectedSkin(v);
        } else if (v.getId() == R.id.ivBlackEyes) {
            ((FirstLoginActivity) getActivity()).getUser().setEyes("Black");
            setSelectedEyesColour(v);
        } else if (v.getId() == R.id.ivBrownEyes) {
            ((FirstLoginActivity) getActivity()).getUser().setEyes("Brown");
            setSelectedEyesColour(v);
        } else if (v.getId() == R.id.ivBlueEyes) {
            ((FirstLoginActivity) getActivity()).getUser().setEyes("Blue");
            setSelectedEyesColour(v);
        } else if (v.getId() == R.id.ivGreenEyes) {
            ((FirstLoginActivity) getActivity()).getUser().setEyes("Green");
            setSelectedEyesColour(v);
        } else if (v.getId() == R.id.ivGreyEyes) {
            ((FirstLoginActivity) getActivity()).getUser().setEyes("Grey");
            setSelectedEyesColour(v);
        } else if (v.getId() == R.id.bNextProfilFragment) {
            User user = ((FirstLoginActivity) getActivity()).getUser();
            Log.e("user", user.toString());

            if (!user.getSkinColour().equals("")) {
                if (!user.getEyes().equals("")) {
                    if (!TextUtils.isEmpty(etWeight.getText())) {
                        if (!TextUtils.isEmpty(etSize.getText())) {
                            ((FirstLoginActivity) getActivity()).getUser().setSize(etSize.getText().toString());
                            ((FirstLoginActivity) getActivity()).getUser().setWeight(etWeight.getText().toString());
                            ((FirstLoginActivity) getActivity()).getUser().setShape(spBodyShape.getSelectedItem().toString());
                            ((FirstLoginActivity) getActivity()).getViewPager().setCurrentItem(1);

                        } else {
                            Toast.makeText(getActivity(), "Please enter your Size", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please enter your weight , Hint you are free to lie about it ;)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select your eyes color", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "Please selec your skin colour", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void setSelectedSkin(View v) {
        if (v.getId() == R.id.ivBrownSkin) {
            cvBrownSkin.setCardBackgroundColor(Color.CYAN);
            cvWhiteSkin.setCardBackgroundColor(Color.DKGRAY);
            cvBlackSkin.setCardBackgroundColor(Color.DKGRAY);
            cvYellowSkin.setCardBackgroundColor(Color.DKGRAY);
        } else if (v.getId() == R.id.ivYellowSkin) {
            cvBrownSkin.setCardBackgroundColor(Color.DKGRAY);
            cvWhiteSkin.setCardBackgroundColor(Color.DKGRAY);
            cvBlackSkin.setCardBackgroundColor(Color.DKGRAY);
            cvYellowSkin.setCardBackgroundColor(Color.CYAN);

        } else if (v.getId() == R.id.ivWhiteSkin) {

            cvBrownSkin.setCardBackgroundColor(Color.DKGRAY);
            cvWhiteSkin.setCardBackgroundColor(Color.CYAN);
            cvBlackSkin.setCardBackgroundColor(Color.DKGRAY);
            cvYellowSkin.setCardBackgroundColor(Color.DKGRAY);
        } else if (v.getId() == R.id.ivBlackSkin) {

            cvBrownSkin.setCardBackgroundColor(Color.DKGRAY);
            cvWhiteSkin.setCardBackgroundColor(Color.DKGRAY);
            cvBlackSkin.setCardBackgroundColor(Color.CYAN);
            cvYellowSkin.setCardBackgroundColor(Color.DKGRAY);
        }

    }

    public void setSelectedEyesColour(View v) {
        if (v.getId() == R.id.ivGreenEyes) {
            cvGreenEyes.setCardBackgroundColor(Color.CYAN);
            cvGreyEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlackEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlueEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBrownEyes.setCardBackgroundColor(Color.DKGRAY);
        } else if (v.getId() == R.id.ivBlackEyes) {
            cvGreenEyes.setCardBackgroundColor(Color.DKGRAY);
            cvGreyEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlackEyes.setCardBackgroundColor(Color.CYAN);
            cvBlueEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBrownEyes.setCardBackgroundColor(Color.DKGRAY);
        } else if (v.getId() == R.id.ivBrownEyes) {
            cvGreenEyes.setCardBackgroundColor(Color.DKGRAY);
            cvGreyEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlackEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlueEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBrownEyes.setCardBackgroundColor(Color.CYAN);
        } else if (v.getId() == R.id.ivBlueEyes) {
            cvGreenEyes.setCardBackgroundColor(Color.DKGRAY);
            cvGreyEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlackEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlueEyes.setCardBackgroundColor(Color.CYAN);
            cvBrownEyes.setCardBackgroundColor(Color.DKGRAY);
        } else if (v.getId() == R.id.ivGreyEyes) {
            cvGreenEyes.setCardBackgroundColor(Color.DKGRAY);
            cvGreyEyes.setCardBackgroundColor(Color.CYAN);
            cvBlackEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBlueEyes.setCardBackgroundColor(Color.DKGRAY);
            cvBrownEyes.setCardBackgroundColor(Color.DKGRAY);
        }
    }
}
