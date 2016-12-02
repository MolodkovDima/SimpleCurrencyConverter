package com.example.dimka.currencyconverter.readers;

import android.content.Context;

import com.example.dimka.currencyconverter.CurrencyRates;
import com.example.dimka.currencyconverter.database.DB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONReader {

    private static final String NAME_FILE = "my_json";
    private final Context context;

    public JSONReader(Context context) {
        this.context = context;
    }

    public CurrencyRates readJson() {
        DB db = new DB(context);
        final String json = db.getJson();
        if (json == null)
            return null;
        JSONObject daJsonObject = null;
        try{
            daJsonObject = new JSONObject(json);
            final String bank = daJsonObject.getString("bank");
            final String date = daJsonObject.getString("date");
            final JSONArray items = daJsonObject.getJSONArray("items");
            final LinkedList<LinkedList<Float>> datas = new LinkedList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                LinkedList<Float> data = new LinkedList<>();
                Float buy;
                Float sall;
                data.add(buy = Float.valueOf(item.getString("rateBuy")));
                data.add(sall = Float.valueOf(item.getString("rateSell")));
                datas.add(data);
            }
            return new CurrencyRates(date, bank, datas);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
