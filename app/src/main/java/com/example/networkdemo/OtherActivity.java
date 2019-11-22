package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.networkdemo.model.OilPrice;
import com.example.networkdemo.model.OilPriceBody;
import com.example.networkdemo.model.OilPriceRes;
import com.example.networkdemo.model.WeatherFuture;
import com.example.networkdemo.model.WeatherRealtime;
import com.show.api.ShowApiRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OtherActivity extends AppCompatActivity implements View.OnClickListener {

    //聚合数据
    private static final String WEATHER_URL = "http://apis.juhe.cn/simpleWeather/query";
    private static final String WEATHER_KEY = "027a240e528c9255f1d9531e54b600b7";


    //易源数据
    private static final String APP_ID = "114814";
    private static final String KEY = "0c7e8439287e416495fce81c0e224f99";
    private static final String OIL_URL = "http://route.showapi.com/138-46";

    private TextView tv_textView;
    private Button btn_weather, btn_oil_price, btn_news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_other );
        initView();
    }

    //监听事件
    private void initView() {

        tv_textView = findViewById( R.id.tv_textView );
        btn_weather = findViewById( R.id.btn_weather );
        btn_oil_price = findViewById( R.id.btn_oil_price );
        btn_news = findViewById( R.id.btn_news );

        btn_weather.setOnClickListener( this );
        btn_oil_price.setOnClickListener( this );
        btn_news.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_weather:

                getWeather( "南京" );
                break;


            case R.id.btn_oil_price:
                getOilPrice("江苏");
                break;

            case R.id.btn_news:

                break;

        }


    }

//    油价
    private void getOilPrice(final String province ) {
    new Thread( new Runnable() {
    @Override
    public void run() {
    final String res= new ShowApiRequest(OIL_URL,APP_ID,KEY)
            .addTextPara( "pro",province )
            .post();
      tv_textView.post( new Runnable() {
          @Override
          public void run() {
              OilPriceRes priceRes = JSON.parseObject(res, OilPriceRes.class);
              if(priceRes != null && priceRes.getResCode() == 0) {
                  OilPriceBody body = priceRes.getResBody();
                  if(body != null && body.getRetCode() == 0) {
                      List<OilPrice> prices = body.getList();
                      tv_textView.setText(prices.get(0).toString());
                  }
              }
          }

      } );

    }
} ).start();


    }

    private void getWeather(String city) {
        //1、组装数据类型
        try {
//            String url = WEATHER_URL +"?city" + URLEncoder.encode( city, "utf-8" )+"&key" + WEATHER_KEY  ;

            String url = WEATHER_URL + "?key=" + WEATHER_KEY + "&city=" + URLEncoder.encode(city, "utf-8");

            //2、使用okttp发送请求
            Request request = new Request.Builder().url( url ).build();
            new OkHttpClient().newCall( request ).enqueue( new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Log.e("otherActivity",e.getMessage()  );
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //3、数据处理
                 if (response.isSuccessful()){
                     String json = response.body().string();
                     //用Android自带的JSON解析，将json字符串解析为JSONObject
                     JSONObject obj = JSON.parseObject( json );//FastJson的方法

                     if (obj!=null){
                         //使用Android自带的Json解析，根据key解析
                         JSONObject  result = obj.getJSONObject( "result" );
                         JSONObject realtime = result.getJSONObject( "realtime" );
                         final String city = result.getString( "city" );

                   //利用FastJSON转当前实时天气对象
                 final WeatherRealtime weather = JSON.parseObject( realtime.toJSONString() ,WeatherRealtime.class);
                         JSONArray futureWeather = result.getJSONArray( "future" );
                         final List<WeatherFuture> weatherFutures = JSON.parseArray( futureWeather.toJSONString(),
                                 WeatherFuture.class);

                 runOnUiThread( new Runnable() {
                 @Override
                 public void run() {

                 StringBuilder builder = new StringBuilder(  );
                 builder.append( city ).append( weather ).append( "\n\n" ).append( "5天的天气趋势：" );
                 for (WeatherFuture future: weatherFutures){{
                    builder.append( "\n" ).append( future );
                 }
                 }
                        tv_textView.setText( builder.toString());

                        }
                 } );
                     }
                 }

                 }

            } );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }



}
