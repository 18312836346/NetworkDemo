package com.example.networkdemo;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.networkdemo.model.HttpsUtil;
import com.example.networkdemo.model.Ip;
import com.example.networkdemo.model.IpData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_textView;
    private Button btn_get, btn_post, btn_upload, btn_download;

    private ImageView imageView;
    private ScrollView scrollView;


    private static final String IP_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String IP_URL = IP_BASE_URL + "?ip=112.2.253.251";

    //上传路径
    private static final String UPLOAD_FILE_URL = "https://api.github.com/markdown/raw";

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse( "text/x-markdown;charset=utf-8" );

    private static final String TAG = "OkHttpActivity";

    //下载路径
    private static final String DOWNLOAD_URL = "https://github.com/zhayh/AndroidExample/blob/master/README.md";

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse( "image/png" );
    //上传图片
    private static final String UPLOAD_IMG_URL = "https://api.imgur.com/3/image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ok_http );
        initView();
    }

    private void initView() {

        tv_textView = findViewById( R.id.tv_textView );
        btn_get = findViewById( R.id.btn_get );
        btn_post = findViewById( R.id.btn_post );
        btn_upload = findViewById( R.id.btn_upload );
        btn_download = findViewById( R.id.btn_download );
        imageView = findViewById( R.id.iv_image );
        scrollView = findViewById( R.id.scrollview );


        btn_get.setOnClickListener( this );
        btn_post.setOnClickListener( this );
        btn_upload.setOnClickListener( this );
        btn_download.setOnClickListener( this );


        Glide.with( this )
                .load( "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/" +
                        "it/u=2018939532,1617516463&fm=26&gp=0.jpg" )
                .into( imageView );

    }

    //按钮的点击事件
    @Override
    public void onClick(View v) {

        String path = getFilesDir().getAbsolutePath();

        switch (v.getId()) {
            case R.id.btn_get:
//
//        scrollView.setVisibility( View.VISIBLE );
//        imageView.setVisibility( View.GONE );

                get( IP_URL );

                break;

            case R.id.btn_post:

                Map <String, String> params = new HashMap <>();
                params.put( "ip", "112.2.253.251" );
                post( IP_URL, params );

                break;

            case R.id.btn_upload:
                final String fileName = path + File.separator + "readme.md";
                uploadFile( UPLOAD_FILE_URL, fileName );
//                 uploadImage( UPLOAD_IMG_URL,path + "/yyqx.png" );
                break;

            case R.id.btn_download:
                String path1 = getFilesDir().getAbsolutePath();
                downFile( DOWNLOAD_URL, path1 );
                break;

        }

    }

    //上传文件
    private void uploadFile(String url, String fileName) {

        Request request = new Request.Builder().url( url )
                .post( RequestBody.create( MEDIA_TYPE_MARKDOWN, new File( fileName ) ) )
                .build();

        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall( request ).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                Log.e( TAG, e.getMessage() );

                tv_textView.post( new Runnable() {
                    @Override
                    public void run() {
                        tv_textView.setText( "上传失败，" + e.getMessage() );
                    }
                } );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            tv_textView.setText( "上传成功，" + str );
                        }
                    } );

                } else {

                    Log.d( TAG, response.body().string() );
                }

            }
        } );

    }

//    //上传图片
//    private void uploadImage(String url, final String fileName) {
//        // 1. 创建请求主体RequestBody
//        RequestBody fileBody = (RequestBody) RequestBody.create( MEDIA_TYPE_PNG, new File( fileName ) );
//        RequestBody body = new MultipartBody.Builder()
//                .setType( MultipartBody.FORM )
//                .addFormDataPart( "title", "头像" )
//                .addFormDataPart( "file", fileName, fileBody )
//                .build();
//
//        // 2. 创建请求
//        Request request = new Request.Builder()
//                .url( url )
//                .post( body )
//                .build();
//
//        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
//        client.newCall( request ).enqueue( new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//
//                Log.e( TAG, e.getMessage() );
//
//                tv_textView.post( new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_textView.setText( "上传失败，" + e.getMessage() );
//                    }
//                } );
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (response.isSuccessful()) {
//                    final String str = response.body().string();
//                    runOnUiThread( new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_textView.setText( "上传成功，" + str );
//                        }
//                    } );
//
//                } else {
//
//                    Log.d( TAG, response.body().string() );
//                }
//
//            }
//        } );
//
//
//    }


    //下载文件
    public static void writeFile(InputStream is, String path, String fileName) throws IOException {
        // 1. 根据path创建目录对象，并检查path是否存在，不存在则创建
        File directory = new File( path );
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // 2. 根据path和fileName创建文件对象，如果文件存在则删除
        File file = new File( path, fileName );
        if (file.exists()) {
            file.delete();
        }
        // 3. 创建文件输出流对象，根据输入流创建缓冲输入流对象
        FileOutputStream fos = new FileOutputStream( file );
        BufferedInputStream bis = new BufferedInputStream( is );

        // 4. 以每次1024个字节写入输出流对象
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read( buffer )) != -1) {
            fos.write( buffer, 0, len );

        }
        fos.flush();
        // 5. 关闭输入流、输出流对象
        fos.close();
        bis.close();
    }

    //下载文件
    private void downFile(final String url, final String path) {
        // 1. 创建Requet对象
        Request request = new Request.Builder().url( url ).build();

        // 2. 创建OkHttpClient对象，发送请求，并处理回调
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall( request ).enqueue( new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 1. 获取下载文件的后缀名
                    String ext = url.substring( url.lastIndexOf( "." ) + 1 );
                    // 2. 根据当前时间创建文件名，避免重名冲突
                    final String fileName = System.currentTimeMillis() + "." + ext;
                    // 3. 获取响应主体的字节流
                    InputStream is = response.body().byteStream();
                    // 4. 将文件写入path目录
                    writeFile( is, path, fileName );
                    // 5. 在界面给出提示信息
                    tv_textView.post( new Runnable() {
                        @Override
                        public void run() {
                            tv_textView.setText( fileName + "下载成功，存放在" + path );
                        }
                    } );

                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

                Log.e( TAG, e.getMessage() );
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        tv_textView.setText( "下载文件失败" );
                    }
                } );
            }

        } );

    }


    // get异步请求是在子线程中执行的，需要切换到主线程更新UI

    private void get(String url) {
        // 1. 构造Request
        final Request request = new Request.Builder().url( url )
                .header( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36 " )
                .addHeader( "Accept", "application/json" )
                .get()
                .method( "GET", null )
                .build();

        // 2. 发送请求，并处理回调
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall( request ).enqueue( new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e( "OkHttpActivity", e.getMessage() );
            }

            //回应
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 1. 获取响应主体的json字符串
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    final Ip ip = JSON.parseObject( json, Ip.class );
                    // 2. 使用FastJson库解析json字符串
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            // 3. 根据返回的code判断获取是否成功
                            if (ip.getCode() != 0) {
                                tv_textView.setText( "未获得数据" );

                            } else {
                                // 4. 解析数据
                                IpData data = ip.getIpdata();
                                tv_textView.setText( data.getIp() + "," + data.getCity() );

                            }
                        }
                    } );

                }

            }


        } );


    }


    //post请求
    private void post(String url, Map <String, String> params) {
        // 1. 构建RequestBody
        RequestBody body = setRequestBody( params );
        // 2. 创建Request对象
        Request request = new Request.Builder().url( url ).post( body )
                .header( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36 " )
                .addHeader( "Accept", "application/json" )
                .build();

        // 2. 发送请求，并处理回调
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall( request ).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e( "OkHttpActivity", e.getMessage() );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 1. 获取响应主体的json字符串
                    String json = response.body().string();

                    // 2. 使用FastJson库解析json字符串
                    final Ip ip = JSON.parseObject( json, Ip.class );

                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            // 3. 根据返回的code判断获取是否成功
                            if (ip.getCode() != 0) {
                                tv_textView.setText( "未获得数据" );

                            } else {
                                // 4. 解析数据
                                IpData data = ip.getIpdata();
                                tv_textView.setText( data.getIp() + "," + data.getCity() );

                            }
                        }
                    } );

                }
            }

        } );

    }

    //将请求的参数组装成RequestBody
    private RequestBody setRequestBody(Map <String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add( key, params.get( key ) );

        }
        return builder.build();
    }

}
