package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class OtherActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_textView;
    private Button btn_weather,btn_oil_price,btn_news;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_other );
        initView();
    }

    private void initView() {

        tv_textView = findViewById( R.id.tv_textView );
        scrollView = findViewById( R.id.scrollview );
        btn_weather = findViewById( R.id.btn_weather );
        btn_oil_price = findViewById( R.id.btn_oil_price );
        btn_news = findViewById( R.id.btn_news );

        btn_weather.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_weather :

                break;


            case R.id.btn_oil_price :
                break;

            case R.id.btn_news :

                break;

        }


    }
}
