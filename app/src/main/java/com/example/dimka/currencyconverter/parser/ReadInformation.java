package com.example.dimka.currencyconverter.parser;


import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadInformation {

    private static final String filename = "info";
    private Context context = null;

    public void setContext(Context context) {
        this.context = context;
    }

    public String read() {
        StringBuilder builder = new StringBuilder();
        InputStream stream = context.getResources().openRawResource(
                context.getResources().getIdentifier(filename, "raw", context.getPackageName()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}