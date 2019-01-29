package com.example.admin.cryptoapp;

import android.content.Context;
import android.graphics.Color;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Graph {

    Context context;
    LineChart lineChart;
    BarChart barChart;


    public Graph(LineChart lineChart,BarChart barChart) {
        this.lineChart = lineChart;
        this.barChart = barChart;
    }

    public void _lineGraph (Context context,String Coin)
    {
        final List<Entry> entries = new ArrayList<>();
        final List<BarEntry> barEntries = new ArrayList<>();
        RequestQueue requestQueue =Volley.newRequestQueue(context);
        String URL = "https://min-api.cryptocompare.com/data/histohour?fsym="+Coin+"&tsym=USD&limit=30&aggregate=1&e=CCCAGG";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("Data");

                    int _close[] = new int[jsonArray.length()];
                    String _date[] = new String[jsonArray.length()];
                    int _volume[]= new int[jsonArray.length()];

                    for (int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);

                        _close[i]=jsonObject.getInt("close");
                        _date[i]=jsonObject.getString("time");
                        _volume[i]=jsonObject.getInt("volumeto");


                        entries.add(new Entry(Float.valueOf(_date[i]),_close[i]));
                        barEntries.add(new BarEntry(Float.valueOf(_date[i]),_volume[i]));
                    }
                    DisplayLineGraph(entries);
                    DisplayBarChart(barEntries);
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

    public void DisplayLineGraph(List<Entry> entries)
    {
        LineDataSet lineDataSet = new LineDataSet(entries,"");

        LineData lineData=new LineData(lineDataSet);
        lineDataSet.setColor(Color.rgb(27,156,252));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCircleColor(Color.rgb(27,156,252));

        lineChart.getDescription().setText("Time : ");
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lineChart.getDescription().setText("Time : "+ UNIX_TIME((long) e.getX()));
            }

            @Override
            public void onNothingSelected() {

            }
        });

        lineChart.setData(lineData);
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.TRANSPARENT);
        lineChart.setDrawBorders(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.animateX(2000);
        lineChart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis= lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);
        lineChart.getDescription().setTextSize(15);
        lineChart.getLegend().setEnabled(true);
        YAxis yAxis=lineChart.getAxisRight();
        YAxis yAxis1=lineChart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis1.setEnabled(false);
        lineChart.invalidate();
    }
    public String UNIX_TIME(Long timeStamp)
    {
        Date date = new Date(timeStamp * 1000L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        simpleDateFormat.setTimeZone(TimeZone.getDefault());

        String formatedDate=simpleDateFormat.format(date);

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("HH:mm a");

        Date _24HourDT=null;
        try {
            _24HourDT=_24HourSDF.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return _12HourSDF.format(_24HourDT);
    }

    public void DisplayBarChart(List<BarEntry> barEntries)
    {
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        BarData barData = new BarData(barDataSet);
        barDataSet.setColor(Color.rgb(214,162,232));


        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                barChart.getDescription().setText("Time : " + UNIX_TIME((long) e.getX()));
            }

            @Override
            public void onNothingSelected() {

            }
        });
        barChart.setData(barData);
        barData.setBarWidth(950f);
        barChart.setDrawGridBackground(false);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        barChart.setDrawBorders(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.setTouchEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.animateY(2000);
        barChart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis= barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);
        barChart.getDescription().setTextSize(15);
        barChart.getLegend().setEnabled(true);
        YAxis yAxis=barChart.getAxisRight();
        YAxis yAxis1=barChart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis1.setEnabled(false);
        barChart.invalidate();
    }
}
