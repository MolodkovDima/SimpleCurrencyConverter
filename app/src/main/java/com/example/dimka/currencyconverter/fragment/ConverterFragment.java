package com.example.dimka.currencyconverter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dimka.currencyconverter.R;
import com.example.dimka.currencyconverter.parser.ReadJSON;

import java.util.LinkedList;

public class ConverterFragment extends AbstractTabFragment {

    private static final int  LAYOUT = R.layout.fragment_converter;

    private TextView tv_date;
    private TextView tv_bank;
    private TextView tv_usd_buy;
    private TextView tv_usd_sell;
    private TextView tv_eur_buy;
    private TextView tv_eur_sell;
    private TextView tv_rub_buy;
    private TextView tv_rub_sell;
    private ReadJSON readJSON;


    public static ConverterFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ConverterFragment fragment = new ConverterFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.menu_item_Converter));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_date = (TextView) view.findViewById(R.id.tv_date1);
        tv_bank = (TextView) view.findViewById(R.id.tv_bank);
        tv_usd_buy = (TextView) view.findViewById(R.id.tv_usd_buy);
        tv_usd_sell = (TextView) view.findViewById(R.id.tv_usd_sell);
        tv_eur_buy = (TextView) view.findViewById(R.id.tv_eur_buy);
        tv_eur_sell = (TextView) view.findViewById(R.id.tv_eur_sell);
        tv_rub_buy = (TextView) view.findViewById(R.id.tv_rub_buy);
        tv_rub_sell = (TextView) view.findViewById(R.id.tv_rub_sell);
        initData();
    }

    private void initData() {
        readJSON = new ReadJSON();
        readJSON.readJson();
        tv_date.setText(readJSON.getDate());
        tv_bank.setText(readJSON.getBank());
        LinkedList<LinkedList<String>> datas = new LinkedList<LinkedList<String>>();
        LinkedList<String> usd = new LinkedList<>();
        LinkedList<String> eur = new LinkedList<>();
        LinkedList<String> rub = new LinkedList<>();
        datas = readJSON.getDatas();
        usd.addAll(datas.get(0));
        eur.addAll(datas.get(1));
        rub.addAll(datas.get(2));

        tv_usd_buy.setText(usd.get(0));
        tv_usd_sell.setText(usd.get(1));
        tv_eur_buy.setText(eur.get(0));
        tv_eur_sell.setText(eur.get(1));
        tv_rub_buy.setText(rub.get(0));
        tv_rub_sell.setText(rub.get(1));


    }

    public void setContext(Context context) {
        this.context = context;
    }

}
