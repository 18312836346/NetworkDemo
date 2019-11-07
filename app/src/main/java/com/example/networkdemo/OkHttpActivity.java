package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_textView;
    private Button btn_get,btn_post,btn_upload,btn_download;

    private ImageView imageView;



    private  static final String IP_BASE_URL ="http://ip.taobao.com/service/getIpInfo.php";
    private static  final  String IP_URL=IP_BASE_URL+"?ip=112.2.253.251";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ok_http );
        initView();
    }

    private void initView() {
        tv_textView =findViewById( R.id.tv_textView );
        btn_get = findViewById( R.id.btn_get );
        btn_post = findViewById( R.id.btn_post );
        btn_upload = findViewById( R.id.btn_upload );
        btn_download = findViewById( R.id.btn_download );
        imageView = findViewById( R.id.iv_image );


        btn_get.setOnClickListener( this );
        btn_post.setOnClickListener( this );
        btn_upload.setOnClickListener( this );
        btn_download.setOnClickListener( this );


        Glide.with( this )
                .load( "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/" +
                        "it/u=2018939532,1617516463&fm=26&gp=0.jpg" )
                .into( imageView );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_get :

                break;

            case R.id.btn_post :

                break;

            case R.id.btn_upload :


                break;

            case R.id.btn_download :

                break;

        }

    }
}
