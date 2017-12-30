package com.labidi.wafa.secondchance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.labidi.wafa.secondchance.Entities.Discussion;
import com.labidi.wafa.secondchance.Utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

/**
 * Created by macbook on 29/12/2017.
 */

public abstract class DialogsActivity extends AppCompatActivity
        implements DialogsListAdapter.OnDialogClickListener<Discussion>,
        DialogsListAdapter.OnDialogLongClickListener<Discussion> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Discussion> dialogsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(DialogsActivity.this).load(url).into(imageView);
            }
        };
    }

    @Override
    public void onDialogLongClick(Discussion dialog) {
        AppUtils.showToast(
                this,
                dialog.getDialogName(),
                false);
    }
}