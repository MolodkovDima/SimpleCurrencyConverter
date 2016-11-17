package com.example.dimka.currencyconverter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.example.dimka.currencyconverter.R;

public class DialogInformation extends DialogFragment {

    private View view = null;
    private String info = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_information, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return (builder.setTitle("Information").setView(view)
                .setMessage(toString().valueOf(info))
                .setNegativeButton(android.R.string.ok, null).create());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = getArguments().getString("information");
    }

    public static DialogInformation newInstance(String info) {
        DialogInformation information = new DialogInformation();
        Bundle args = new Bundle();
        args.putString("information", info);
        information.setArguments(args);
        return information;
    }
}
