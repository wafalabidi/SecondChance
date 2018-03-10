package com.esprit.secondchance.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esprit.secondchance.FilterActivity;
import com.esprit.secondchance.R;

/**
 * Created by sofien on 12/01/2018.
 */

public class PublishFragment extends Fragment {
    Button publish  ;
    EditText tvSaying;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_fragment, container, false);
        publish = view.findViewById(R.id.Publish) ;
        tvSaying = view.findViewById(R.id.tvSaying);
        publish.setOnClickListener(viw->{
            ((FilterActivity) getActivity()) .publish(tvSaying.getText().toString());
        });
        return  view ;
    }
}
