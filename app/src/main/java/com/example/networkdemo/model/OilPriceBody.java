package com.example.networkdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OilPriceBody {
    @JSONField(name = "ret_code")
    private int retCode;
    private List<OilPrice> list;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public List <OilPrice> getList() {
        return list;
    }

    public void setList(List <OilPrice> list) {
        this.list = list;
    }
}
