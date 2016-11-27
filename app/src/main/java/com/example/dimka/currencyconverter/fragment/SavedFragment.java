package com.example.dimka.currencyconverter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dimka.currencyconverter.R;

public class SavedFragment extends AbstractTabFragment {

    private static final int  LAYOUT = R.layout.fragment_saved;
    private Context context;
    private View view;

    public static SavedFragment getInstance(Context context) {
        Bundle args = new Bundle();
        SavedFragment fragment = new SavedFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.menu_item_Saved));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
