package com.example.admin.cryptoapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class GetPrice {

    TextView _price, _high, _low, _open, _volume, _tradeID, _lastUpdate;

    PieChart pieChart;

    public GetPrice(TextView _price,TextView _high,TextView _low,TextView _open,TextView _volume,TextView _tradeID,TextView _lastUpdate ,PieChart pieChart) {
        this._price = _price;
        this._high = _high;
        this._low = _low;
        this._open = _open;
        this._volume = _volume;
        this._tradeID = _tradeID;
        this._lastUpdate = _lastUpdate;
        this.pieChart = pieChart;
    }

    public void PriceData(Context context, String coin, final String totalCoin) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = "https://min-api.cryptocompare.com/data/top/exchanges/full?fsym="+coin+"&tsym=USD";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 //Log.d("Main",response.toString());
                try {
                    JSONObject data = response.getJSONObject("Data");
                    JSONObject coinInfo = data.getJSONObject("CoinInfo");
                    JSONObject AggregatedData = data.getJSONObject("AggregatedData");
                    try {

                        String totalCoinMined = coinInfo.getString("TotalCoinsMined");
                        DisplayGraph(totalCoin,totalCoinMined);

                    } catch (Error e)
                    {

                    }
                    try {
                        String Price = AggregatedData.getString("PRICE");
                        _price.setText("Price: " +String.valueOf(Math.round(Float.parseFloat(Price))));

                    } catch (Error e) {
                        _price.setText("Data not available");
                    }

                    try {
                        String Open = AggregatedData.getString("OPENDAY");
                        _open.setText("Open: "+String.format("%.2f", Double.valueOf(Open)));

                    } catch (Error e) {
                        _open.setText("Data not available");
                    }

                    try {
                        String High = AggregatedData.getString("HIGHDAY");
                        _high.setText("High: "+String.format("%.2f", Float.valueOf(High)));

                    } catch (Error e) {
                        _high.setText("Data not available");
                    }
                    try {
                        String Low = AggregatedData.getString("LOWDAY");
                        _low.setText("Low: "+String.format("%.2f", Float.valueOf(Low)));

                    } catch (Error e) {
                        _low.setText("Data not available");
                    }
                    try {
                        String Volume = AggregatedData.getString("VOLUMEDAY");
                        _volume.setText("Volume: "+String.valueOf(Math.round(Float.parseFloat(Volume))));

                    } catch (Error e) {
                        _volume.setText("Data not available");
                    }
                    try {
                        String TradeID = AggregatedData.getString("LASTTRADEID");
                        _tradeID.setText("Last Trade ID: "+String.format("%.2f", Float.valueOf(TradeID)));

                    } catch (Error e) {
                        _tradeID.setText("Data not available");
                    }
                    try {
                        String LastUpdate = AggregatedData.getString("LASTUPDATE");
                        String convert = unix_time(Long.parseLong(LastUpdate));
                        _lastUpdate.setText("Last Update: "+ convert);

                    } catch (Error e) {
                        _lastUpdate.setText("Data not available");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public String unix_time(Long timestamp) {

        Date date = new Date(timestamp * 1000L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        simpleDateFormat.setTimeZone(TimeZone.getDefault());

        String formatedDate=simpleDateFormat.format(date);

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("HH:mm a");

        Date _24HourDT=null;

        try{

            _24HourDT =_24HourSDF.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return _12HourSDF.format(_24HourDT);

    }

    public void DisplayGraph(String totalCoin,String totalCoinMined)
    {
        ArrayList<PieEntry> yvalue= new ArrayList<>();
        yvalue.add(new PieEntry(Float.parseFloat(totalCoin) ,"Total Coin"));
        yvalue.add(new PieEntry(Float.parseFloat(totalCoinMined), "Total Coin Mined"));

        PieDataSet pieDataSet = new PieDataSet(yvalue,"");

        ArrayList<Integer> colors= new ArrayList<>();
        colors.add(Color.rgb(249,127,81));
        colors.add(Color.rgb(109,33,79));

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(false);
        pieChart.animateXY(4000,12000);
        pieData.setValueTextSize(12);
        pieData.setValueTextColor(Color.BLACK);


    }
}

