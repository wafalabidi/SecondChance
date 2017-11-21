package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labidi.wafa.secondchance.Adapters.RvHomeAdapter;
import com.labidi.wafa.secondchance.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sofien on 20/11/2017.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rvHome)
    RecyclerView rcHome ;
    RvHomeAdapter rvHomeAdapter ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(inflater == null ){
            inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = inflater.inflate(R.layout.home_fragment , null ) ;
        ButterKnife.bind(this, view) ;
        rvHomeAdapter = new RvHomeAdapter(getActivity() , null) ;
        rcHome = view.findViewById(R.id.rvHome);
        final LinearLayoutManager layoutManager = new  LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcHome.setLayoutManager(layoutManager);
        rcHome.setAdapter(rvHomeAdapter);

        return view;
    }



}
