package com.labidi.wafa.secondchance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.labidi.wafa.secondchance.Entities.Discussion;
import com.labidi.wafa.secondchance.Entities.Messages;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;

/**
 * Created by macbook on 29/12/2017.
 */

public class DialogListActivity extends DialogsActivity {

    private ArrayList<Thread> dialogs;

    public static void open(Context context) {
        context.startActivity(new Intent(context, DialogListActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Discussion dialog) {
        DialogListActivity.open(this);
    }

    private void initAdapter() {
        ArrayList<Discussion> discussions = new  ArrayList<Discussion> () ;
        discussions.add(new Discussion("1","psika"));
        super.dialogsAdapter = new DialogsListAdapter<>(super.imageLoader);
        super.dialogsAdapter.setItems(discussions);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    //for example
    private void onNewMessage(String dialogId, Messages message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    //for example
    private void onNewDialog(Discussion dialog) {
        dialogsAdapter.addItem(dialog);
    }
}