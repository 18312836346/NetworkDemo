package com.example.networkdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Ip implements Serializable {
     private  int code;

     //保证和json文件的类型一样
     @JSONField(name = "data")
     private IpData ipdata;


//get、set方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpData getIpdata() {
        return ipdata;
    }

    public void setIpdata(IpData ipdata) {
        this.ipdata = ipdata;
    }

    @Override
    public String toString() {
        return "Ip{" +
                "code=" + code +
                ", ipdata=" + ipdata +
                '}';
    }
}
