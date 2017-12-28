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

public class InformationFragment extends Fragment {
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

   }