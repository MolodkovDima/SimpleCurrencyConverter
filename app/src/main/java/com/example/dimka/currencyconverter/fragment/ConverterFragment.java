package com.example.dimka.currencyconverter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dimka.currencyconverter.CurrencyRates;
import com.example.dimka.currencyconverter.R;
import com.example.dimka.currencyconverter.database.DB;
import com.example.dimka.currencyconverter.readers.JSONReader;
import com.example.dimka.currencyconverter.readers.MyXMLReader;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

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

    public static ConverterFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ConverterFragment fragment = new ConverterFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.menu_item_Converter));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
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
        tv_date = (TextView) view.findViewById(R.id.tv_date);
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
        readXML();
        final JSONReader reader = new JSONReader(getActivity());
        final CurrencyRates currencyRates = reader.readJson();

        tv_date.setText(tv_date.getText() + " " + currencyRates.getDate());
        tv_bank.setText(currencyRates.getBank());
        LinkedList<LinkedList<Float>> datas = new LinkedList<LinkedList<Float>>();
        LinkedList<Float> usd = new LinkedList<>();
        LinkedList<Float> eur = new LinkedList<>();
        LinkedList<Float> rub = new LinkedList<>();
        datas = currencyRates.getDatas();
        usd.addAll(datas.get(0));
        eur.addAll(datas.get(1));
        rub.addAll(datas.get(2));

        tv_usd_buy.setText(usd.get(0).toString());
        tv_usd_sell.setText(usd.get(1).toString());
        tv_eur_buy.setText(eur.get(0).toString());
        tv_eur_sell.setText(eur.get(1).toString());
        tv_rub_buy.setText(rub.get(0).toString());
        tv_rub_sell.setText(rub.get(1).toString());
    }

    private void readXML(){
        final MyXMLReader xmlReader = new MyXMLReader();
        xmlReader.execute();
        final String json;
        DB db = new DB(getActivity());
        try {
            json = xmlReader.get();
            db.setJson(json);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
