package com.example.dimka.currencyconverter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.dimka.currencyconverter.ChangeStyles;
import com.example.dimka.currencyconverter.R;
import com.example.dimka.currencyconverter.database.DB;

public class DialogStyles extends DialogFragment {

    private DB db;
    private Cursor cursor;
    private String data[] = {"Blue", "Green", "Orange"};
    private View view = null;
    private ChangeStyles styles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(getActivity());
        db.open();
        cursor = db.getAllDate();
        getActivity().startManagingCursor(cursor);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_style, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Styles");
        builder.setSingleChoiceItems(data, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lv = ((AlertDialog) dialog).getListView();
                if (which == Dialog.BUTTON_POSITIVE) {
                    Log.d("myLog", "pos = " + lv.getCheckedItemPosition());
                } else {
                    Log.d("myLog", "witch = " + lv.getCheckedItemPosition());
                }
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lv = ((AlertDialog) dialog).getListView();
                if (which == Dialog.BUTTON_POSITIVE) {
                    Log.d("myLog", "pos = " + lv.getCheckedItemPosition());
                    styles = new ChangeStyles();
                    styles.setPosition(lv.getCheckedItemPosition());
                    restartActivity(lv.getCheckedItemPosition());
                } else {
                    Log.d("myLog", "witch = " + lv.getCheckedItemPosition());
                }
            }
        });
        return builder.create();
    }

    private void restartActivity(int post){
        if (post >= 0 || styles.getPosition() != post){
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            getActivity().finish();
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }
}
