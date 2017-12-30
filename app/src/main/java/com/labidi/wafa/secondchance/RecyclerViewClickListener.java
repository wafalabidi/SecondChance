package com.labidi.wafa.secondchance;

import android.view.View;

/**
 * Created by macbook on 26/12/2017.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);}
