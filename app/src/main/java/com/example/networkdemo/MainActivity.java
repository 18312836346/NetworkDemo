package com.example.networkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_httpURL;
    private Button btn_okHttp;
    private Button btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initView();
    }

    private void initView() {
        btn_httpURL =findViewById( R.id.btn_httpURL );
        btn_okHttp = findViewById( R.id.btn_okHttp );
        btn_other =findViewById( R.id.btn_other );
        btn_httpURL.setOnClickListener( this );
        btn_okHttp.setOnClickListener( this );
        btn_other.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_httpURL :
                Intent intent = new Intent( MainActivity.this,HttpURLConnectionActivity.class );
                startActivity( intent );
                break;

            case R.id.btn_okHttp :
                Intent intent1 = new Intent( MainActivity.this,OkHttpActivity.class );
                startActivity( intent1);
                break;

            case R.id.btn_other :
                Intent intent2 = new Intent( MainActivity.this,OtherActivity.class );
                startActivity( intent2);
                break;


        }


    }
}
