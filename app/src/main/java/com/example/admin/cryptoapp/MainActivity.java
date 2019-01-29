package com.example.admin.cryptoapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView _price,_high,_low,_open,_volume,_tradeID,_lastUpdate,_coinName;
    GetPrice get_price;
    PieChart pieChart;
    LineChart lineChart;
    Graph graph;
    BarChart barChart;
    Exchange exchange;
    EditText value;
    TextView answer,_currency;
    ImageButton send;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _price = findViewById(R.id.price);
        _open=findViewById(R.id.Open);
        _high=findViewById(R.id.high);
        _low=findViewById(R.id.Low);
        _volume=findViewById(R.id.Volume);
        _tradeID=findViewById(R.id.TradeID);
        _lastUpdate=findViewById(R.id.Update);
        _coinName=findViewById(R.id.coinName);
        pieChart=findViewById(R.id.PieChart);
        lineChart=findViewById(R.id.LineChart);
        barChart=findViewById(R.id.BarChart);
        send=findViewById(R.id.send);
        value=findViewById(R.id.Value);
        answer=findViewById(R.id.answer);
        _currency=findViewById(R.id.currency);
        imageView=findViewById(R.id.imageView);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        get_price=new GetPrice(_price,_high,_low,_open,_volume,_tradeID,_lastUpdate,pieChart);
        graph=new Graph(lineChart,barChart);
        exchange=new Exchange(value,answer);

        get_price.PriceData(MainActivity.this, "BTC","21000000");
        graph._lineGraph(MainActivity.this,"BTC");
        exchange.coin_exchange(MainActivity.this,"BTC");
        _currency.setText("BTC");
        imageView.setImageResource(R.drawable.ic_btc);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchange.coin_exchange(MainActivity.this,String.valueOf(_currency));

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case R.id.BitCoin :{
                get_price.PriceData(MainActivity.this, "BTC","21000000");
                graph._lineGraph(MainActivity.this,"BTC");
                _coinName.setText("BitCoin");
                exchange.coin_exchange(MainActivity.this,"BTC");
                _currency.setText("BTC");
                imageView.setImageResource(R.drawable.ic_btc);
                break;
            }
            case R.id.LiteCoin :{
                get_price.PriceData(MainActivity.this, "LTC","84000000");
                graph._lineGraph(MainActivity.this,"LTC");
                _coinName.setText("LiteCoin");
                exchange.coin_exchange(MainActivity.this,"LTC");
                _currency.setText("LTC");
                imageView.setImageResource(R.drawable.ic_ltc);
                break;
            }
            case R.id.BitCoinCash :{
                get_price.PriceData(MainActivity.this, "BCH","21000000");
                graph._lineGraph(MainActivity.this,"BCH");
                _coinName.setText("BitCoin Cash");
                exchange.coin_exchange(MainActivity.this,"BCH");
                _currency.setText("BCH");
                imageView.setImageResource(R.drawable.ic_bch);
                break;
            }
            case R.id.NEO :{
                get_price.PriceData(MainActivity.this, "NEO","100000000");
                graph._lineGraph(MainActivity.this,"NEO");
                _coinName.setText("NEO");
                exchange.coin_exchange(MainActivity.this,"NEO");
                _currency.setText("NEO");
                imageView.setImageResource(R.drawable.ic_neo);
                break;
            }
            case R.id.IOTA :{
                get_price.PriceData(MainActivity.this, "IOT","100000000");
                graph._lineGraph(MainActivity.this,"IOT");
                _coinName.setText("IOTA");
                exchange.coin_exchange(MainActivity.this,"IOT");
                _currency.setText("IOT");
                imageView.setImageResource(R.drawable.ic_iota);
                break;
            }
            case R.id.Ethereum :{
                get_price.PriceData(MainActivity.this, "ETH","1000000000");
                graph._lineGraph(MainActivity.this,"ETH");
                _coinName.setText("Ethereum");
                exchange.coin_exchange(MainActivity.this,"ETH");
                _currency.setText("ETH");
                imageView.setImageResource(R.drawable.ic_eth);
                break;
            }
            case R.id.DogeCoin :{
                get_price.PriceData(MainActivity.this, "DOGE","21000000000");
                graph._lineGraph(MainActivity.this,"DOGE");
                _coinName.setText("DogeCoin");
                exchange.coin_exchange(MainActivity.this,"DOGE");
                _currency.setText("DOGE");
                imageView.setImageResource(R.drawable.ic_doge);
                break;
            }
            case R.id.Ripple:{
                get_price.PriceData(MainActivity.this, "TRX","38000000000");
                graph._lineGraph(MainActivity.this,"TRX");
                _coinName.setText("Ripple");
                exchange.coin_exchange(MainActivity.this,"TRX");
                _currency.setText("TRX");
                imageView.setImageResource(R.drawable.ic_xrp);
                break;
            }
            case R.id.Cardano:{
                get_price.PriceData(MainActivity.this, "ADA","45000000000");
                graph._lineGraph(MainActivity.this,"ADA");
                _coinName.setText("Cardano");
                exchange.coin_exchange(MainActivity.this,"ADA");
                _currency.setText("ADA");
                imageView.setImageResource(R.drawable.ic_ada);
                break;
            }
            case R.id.Stellar:{
                get_price.PriceData(MainActivity.this, "XVG","104323820476");
                graph._lineGraph(MainActivity.this,"XVG");
                _coinName.setText("Stellar");
                exchange.coin_exchange(MainActivity.this,"XVG");
                _currency.setText("XVG");
                imageView.setImageResource(R.drawable.ic_xlm);
                break;
            }
            case R.id.Monero:{
                get_price.PriceData(MainActivity.this, "QTUM","19000000");
                graph._lineGraph(MainActivity.this,"QTUM");
                _coinName.setText("Monero");
                exchange.coin_exchange(MainActivity.this,"QTUM");
                _currency.setText("QTUM");
                imageView.setImageResource(R.drawable.ic_xmr);
                break;
            }


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
