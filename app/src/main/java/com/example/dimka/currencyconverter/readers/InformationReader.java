package com.example.dimka.currencyconverter.readers;


import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InformationReader {

    private static final String filename = "info";
    private Context context = null;

    public InformationReader(Context context) {
        this.context = context;
    }

    public String read() {
        final StringBuilder builder = new StringBuilder();
        final InputStream stream = context
                .getResources()
                .openRawResource(
                context
                        .getResources()
                        .getIdentifier(filename, "raw", context.getPackageName()));
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
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