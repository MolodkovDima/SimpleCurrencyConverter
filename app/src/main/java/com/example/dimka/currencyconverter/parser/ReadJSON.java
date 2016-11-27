package com.example.dimka.currencyconverter.parser;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class ReadJSON {

    private static final String NAME_FILE = "my_json";
    private InputStream inputStream = null;
    private static Context context;
    private String date;

    private String bank;

    private LinkedList<LinkedList<String>> datas;
    public static void setContext(Context context) {
        ReadJSON.context = context;
    }

    String json = null;
    private void openJson() {
        try {
            inputStream = context.getResources()
                    .openRawResource(context.getResources()
                            .getIdentifier(NAME_FILE, "raw", context.getPackageName()));
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readJson() {
        openJson();
        JSONObject daJsonObject = null;
        try{
            daJsonObject = new JSONObject(json);
            bank = daJsonObject.getString("bank");
            date = daJsonObject.getString("date");
            Log.d("MyLog", date);
            JSONArray items = daJsonObject.getJSONArray("items");
            datas = new LinkedList<LinkedList<String>>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                LinkedList<String> data = new LinkedList<>();
                data.add(item.getString("rateBuy"));
                data.add(item.getString("rateSell"));
                datas.add(data);
                Log.d("MyLog", data.toString());
            }
            Log.d("MyLog", datas.toString() + " " + date + " " + bank);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<LinkedList<String>> getDatas() {
        return datas;
    }

    public String getBank() {
        return bank;
    }

    public String getDate() {
        return date;
    }
}
