package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("orders")
public class Orders {

    @TableId(type = IdType.AUTO)
    private Integer ordersid;
    private Integer userid;
    private Integer goodsid;
    private String ordersimg;
    private String ordersname;
    private Integer ordersnum;
    private Double ordersprice;

    public Integer getOrdersid() {
        return ordersid;
    }

    public void setOrdersid(Integer ordersid) {
        this.ordersid = ordersid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public String getOrdersimg() {
        return ordersimg;
    }

    public void setOrdersimg(String ordersimg) {
        this.ordersimg = ordersimg;
    }

    public String getOrdersname() {
        return ordersname;
    }

    public void setOrdersname(String ordersname) {
        this.ordersname = ordersname;
    }

    public Integer getOrdersnum() {
        return ordersnum;
    }

    public void setOrdersnum(Integer ordersnum) {
        this.ordersnum = ordersnum;
    }

    public Double getOrdersprice() {
        return ordersprice;
    }

    public void setOrdersprice(Double ordersprice) {
        this.ordersprice = ordersprice;
    }
}
