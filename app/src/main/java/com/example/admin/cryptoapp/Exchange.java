package com.example.admin.cryptoapp;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Exchange {

    EditText value;
    TextView answer;

    public Exchange(EditText value, TextView answer) {
        this.value = value;
        this.answer = answer;
    }

    public void coin_exchange(Context context, final String coin)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL="https://min-api.cryptocompare.com/data/price?fsym="+coin+"&tsyms=RSD";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int _newcurrency = response.getInt("RSD");

                    String number = value.getText().toString();

                    float result = Math.round(Float.valueOf(number)*_newcurrency);

                    answer.setText(String.valueOf(result));
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
}
