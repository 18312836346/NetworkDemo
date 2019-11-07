package com.example.networkdemo;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.networkdemo.model.HttpsUtil;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.namespace.NamespaceContext;

public class NetworkUtils {

    //get请求的网络请求方法
    public static String get(String urlPath) {



        try {
             //1、将url字符串转为URL对象
            URL url = new URL( urlPath );
            //2、获得HttpURLConnection对象
         HttpURLConnection   connection  = (HttpURLConnection) url.openConnection();
           //3、设置连接的相关参数
            //默认为GET
            connection.setRequestMethod( "GET" );
            //不使用缓存
            connection.setUseCaches( false );
            //设置连接超时时间
            connection.setConnectTimeout( 15000 );
            //设置读取超时时间
            connection.setReadTimeout( 15000 );
            //设置Http Header
            connection.setRequestProperty( "Connection","keep-alive" );
            //设置模拟浏览器访问
            connection.setRequestProperty( "User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N)" +
                    " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Mobile Safari/537.36");
            // 设置是否从httpUrlConnection读入，默认true
            connection.setDoInput( true );

            //4、配置https的证书
         if ("https".equalsIgnoreCase( url.getProtocol())){

             ((HttpsURLConnection)connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());
         }

         //5、进行数据的读取，首先判断响应码是否为200
         if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){

             //获得输入流
              InputStream  is = connection.getInputStream();
              // 包装字节流为字符流
                BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
                //读取数据
                StringBuilder response = new StringBuilder(  );
                String line;
                while ((line=reader.readLine())!=null){
                    response.append( line );
              // 6. 关闭资源
                    is.close();
                    connection.disconnect();
                    //7、返回结果
                    return  response.toString();

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

      return null;
}

    // post请求的网络请求方法
    public static String post(String urlPath, List<NameValuePair> params) {
        // 1. 处理请求参数
        if (params == null || params.size() == 0) {
            return get(urlPath);
        }
        try {
            String body = getParamString(params);
            byte[] data = body.getBytes();

            // 1. 将url字符串转为URL对象
            URL url = new URL(urlPath);
            // 2. 获得HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 3. 设置连接的相关参数
            connection.setRequestMethod("POST"); // 默认为GET
            connection.setUseCaches(false);     // 不使用缓存
            connection.setConnectTimeout(15000);// 设置连接超时时间
            connection.setReadTimeout(15000);   // 设置读取超时时间
            connection.setRequestProperty("Connection", "Keep-Alive");  // 设置Http Header
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");// 设置模拟浏览器访问
            connection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认true;
            connection.setDoOutput(true);

            // 4. 配置https的证书
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                ((HttpsURLConnection) connection).setSSLSocketFactory(
                        HttpsUtil.getSSLSocketFactory());
            }
            // 5. 设置请求头属性, 写入参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.flush();

            // 5. 进行数据的读取，首先判断响应码是否为200
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 获得输入流
                InputStream is = connection.getInputStream();
                // 包装字节流为字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                // 读取数据
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // 6. 关闭资源
                is.close();
                connection.disconnect();
                // 7. 返回结果
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String urlPath, Map<String, String> params) {
        // 1. 处理post参数
        if (params == null || params.size() == 0) {
            return get(urlPath);
        }

        try {
            String body = getParamString(params);
            byte[] data = body.getBytes();

            // 2. 将url字符串转为URL对象
            URL url = new URL(urlPath);
            // 3. 获得HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 4. 设置连接的相关参数
            connection.setRequestMethod("POST");// 设置请求方法为post
            connection.setUseCaches(false);     // 不使用缓存
            connection.setConnectTimeout(10000);// 设置读取超时时间
            connection.setReadTimeout(10000);   // 设置读取超时时间
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");// 设置模拟浏览器访问
            connection.setDoInput(true);    // 设置是否从httpUrlConnection读入，默认true;
            connection.setDoOutput(true);   // 设置为true后才能写入参数

            // 4. 配置https的证书
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                ((HttpsURLConnection) connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());
            }

            // 5. 设置请求头属性, 写入参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream os = connection.getOutputStream();
            os.write(data);

            // 6. 进行数据的读取，首先判断响应码是否为200
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 获得输入流
                InputStream is = connection.getInputStream();
                // 包装字节流为字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                // 读取数据
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // 7. 关闭资源
                os.close();
                is.close();
                connection.disconnect();

                // 8. 返回结果
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 组装请求参数
    private static String getParamString(List<NameValuePair> pairs)
            throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (NameValuePair pair : pairs) {
            if (!TextUtils.isEmpty(builder)) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return builder.toString();
    }


    // 组装请求参数
    private static String getParamString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            String key = URLEncoder.encode(param.getKey(), "UTF-8");
            String value = URLEncoder.encode(param.getValue(), "UTF-8");
            builder.append(key).append('=').append(value);
            if (iterator.hasNext()) {
                builder.append('&');
            }
        }
        return builder.toString();
    }


}
