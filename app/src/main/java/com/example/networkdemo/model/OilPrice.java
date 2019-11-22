package com.example.networkdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OilPrice {

    /**
     * showapi_res_error :
     * showapi_res_id : f46d60986d7f467b98e36786c33fb88c
     * showapi_res_code : 0
     * showapi_res_body : {"ret_code":0,"list":[{"prov":"江苏","p90":"","p0":"6.34","p95":"7.10","p97":"","p98":"8.02","p89":"6.22","p92":"6.68","ct":"2019-11-15 07:02:00.452","p93":""}]}
     */


    private String prov;
    private String p0;
    private String p95;
    private String p98;
    private String p92;

    @JSONField(name = "ct", format = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date date;

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getP0() {
        return p0;
    }

    public void setP0(String p0) {
        this.p0 = p0;
    }

    public String getP95() {
        return p95;
    }

    public void setP95(String p95) {
        this.p95 = p95;
    }

    public String getP98() {
        return p98;
    }

    public void setP98(String p98) {
        this.p98 = p98;
    }

    public String getP92() {
        return p92;
    }

    public void setP92(String p92) {
        this.p92 = p92;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OilPrice{" +
                "prov='" + prov + '\'' +
                ", p0='" + p0 + '\'' +
                ", p95='" + p95 + '\'' +
                ", p98='" + p98 + '\'' +
                ", p92='" + p92 + '\'' +
                ", date=" + date +
                '}';
    }
}
