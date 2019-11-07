package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class HttpURLConnectionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_textView;
    private Button btn_get,btn_post,btn_upload,btn_download;
    private ScrollView scrollView;
    private ImageView imageView;

    private  static final String IP_BASE_URL ="http://ip.taobao.com/service/getIpInfo.php";
    private static  final  String IP_URL=IP_BASE_URL+"?ip=112.2.253.251";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_http_urlconnection );
        initView();

    }

    private void initView() {

        tv_textView =findViewById( R.id.tv_textView );
        btn_get = findViewById( R.id.btn_get );
        btn_post = findViewById( R.id.btn_post );
        btn_upload = findViewById( R.id.btn_upload );
        btn_download = findViewById( R.id.btn_download );
//        imageView = findViewById( R.id.iv_image );
        scrollView = findViewById( R.id.scrollview );

        btn_get.setOnClickListener( this );
        btn_post.setOnClickListener( this );
        btn_upload.setOnClickListener( this );
        btn_download.setOnClickListener( this );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
//
//                scrollView.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.GONE);

                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        final String result = NetworkUtils.get( IP_URL );

                        if (result != null) {
                            Log.d( "MainActivity", result );
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    tv_textView.setText( result );
                                }
                            } );

                        } else {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    tv_textView.setText( "数据为null" );
                                }
                            } );
                        }
                    }
                } ).start();

                break;

            case R.id.btn_post:
//
//                scrollView.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.GONE);

                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        List <NameValuePair> params = new ArrayList <>();
                        params.add( new BasicNameValuePair( "ip", "112.2.253.251" ) );
                        final String result = NetworkUtils.post( IP_BASE_URL, params );

                        if (result != null) {
                            Log.d( "MainActivity", result );

                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    tv_textView.setText( result );
                                }
                            } );


                        } else {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    tv_textView.setText( "请求失败，未获得数据" );
                                }
                            } );
                        }
                    }
                } ).start();


                break;

            case R.id.btn_upload :


                break;

            case R.id.btn_download :

                break;

        }

    }

}
