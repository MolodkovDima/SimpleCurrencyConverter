package com.example.dimka.currencyconverter.readers;


import android.os.AsyncTask;
import android.util.Log;

import com.example.dimka.currencyconverter.DateAndTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MyXMLReader extends AsyncTask<Void, Void, String> {

    private final static String FILE_PARSE = "http://bank-ua.com/export/exchange_rate_cash.xml";
    private NodeList nodeList;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        LinkedHashMap<String, LinkedList<Float>> datas = new LinkedHashMap<>();
        try {
            final URL url = new URL(FILE_PARSE);
            final DocumentBuilderFactory dbf = DocumentBuilderFactory
                    .newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                final Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    datas = getMap(eElement, datas);
                }
            }
            Log.d("doInBackground", initJSON(datas));
            return initJSON(datas);
        } catch (Exception e) {
            Log.d("MyLogSyncTasc", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String getNode(String sTag, Element eElement) {
        final NodeList nList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        final Node nValue = nList.item(0);
        return nValue.getNodeValue();
    }

    private LinkedHashMap<String, LinkedList<Float>>
    getMap(Element eElement, LinkedHashMap<String, LinkedList<Float>> datas) {
        String nameCorrency;
        final LinkedList<Float> EUR = new LinkedList<>();
        final LinkedList<Float> USD = new LinkedList<>();
        final LinkedList<Float> RUB = new LinkedList<>();
        if (getNode("bankName", eElement).equals("ПУМБ")) {
            Float buy;
            Float sale;
            nameCorrency = (getNode("codeAlpha", eElement));
            if (getNode("codeAlpha", eElement).equals("EUR")){
                EUR.add(buy = Float.valueOf(getNode("rateBuy", eElement)));
                EUR.add(sale = Float.valueOf(getNode("rateSale", eElement)));
                datas.put(nameCorrency, EUR);
            }
            if (getNode("codeAlpha", eElement).equals("USD")){
                USD.add(buy = Float.valueOf(getNode("rateBuy", eElement)));
                USD.add(sale = Float.valueOf(getNode("rateSale", eElement)));
                datas.put(nameCorrency, USD);
            }
            if (getNode("codeAlpha", eElement).equals("RUB")){
                RUB.add(buy = Float.valueOf(getNode("rateBuy", eElement)));
                RUB.add(sale = Float.valueOf(getNode("rateSale", eElement)));
                datas.put(nameCorrency, RUB);
            }
        }
        return datas;
    }

    private String initJSON(LinkedHashMap<String, LinkedList<Float>> datas) {
        LinkedList<String> nameCurrency = new LinkedList<>();
        final LinkedList<Float> eutList = new LinkedList<>();
        final LinkedList<Float> usdList = new LinkedList<>();
        final LinkedList<Float> rubList = new LinkedList<>();

        eutList.addAll(datas.get("EUR"));
        usdList.addAll(datas.get("USD"));
        rubList.addAll(datas.get("RUB"));

        final JSONArray items = new JSONArray();
        final JSONObject eur = new JSONObject();
        final JSONObject usd = new JSONObject();
        final JSONObject rub = new JSONObject();
        final JSONObject resJson = new JSONObject();

        for (Map.Entry<String, LinkedList<Float>> entry: datas.entrySet()){
            nameCurrency.add(entry.getKey());
        }

        try {
            resJson.put("bank", "ПУМБ");
            resJson.put("date", new DateAndTime().getDate());
            usd.put("id", 1);
            usd.put("name", nameCurrency.get(1));
            usd.put("rateBuy", usdList.get(0));
            usd.put("rateSell", usdList.get(1));
            eur.put("id", 2);
            eur.put("name", nameCurrency.get(0));
            eur.put("rateBuy", eutList.get(0));
            eur.put("rateSell", eutList.get(1));
            rub.put("id", 3);
            rub.put("name", nameCurrency.get(2));
            rub.put("rateBuy", rubList.get(0));
            rub.put("rateSell", rubList.get(1));
            items.put(usd);
            items.put(eur);
            items.put(rub);

            resJson.put("items", items);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resJson.toString();
    }
}
