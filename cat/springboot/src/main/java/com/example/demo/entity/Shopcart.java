package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("shopcart")
public class Shopcart {

    @TableId(type = IdType.AUTO)
    private Integer shopcartid;
    private Integer userid;
    private Integer goodsid;
    private String shopcartimg;
    private String shopcartname;
    private Integer shopcartnum;
    private Double shopcartprice;

    public Integer getShopcartid() {
        return shopcartid;
    }

    public void setShopcartid(Integer shopcartid) {
        this.shopcartid = shopcartid;
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

    public String getShopcartimg() {
        return shopcartimg;
    }

    public void setShopcartimg(String shopcartimg) {
        this.shopcartimg = shopcartimg;
    }

    public String getShopcartname() {
        return shopcartname;
    }

    public void setShopcartname(String shopcartname) {
        this.shopcartname = shopcartname;
    }

    public Integer getShopcartnum() {
        return shopcartnum;
    }

    public void setShopcartnum(Integer shopcartnum) {
        this.shopcartnum = shopcartnum;
    }

    public Double getShopcartprice() {
        return shopcartprice;
    }

    public void setShopcartprice(Double shopcartprice) {
        this.shopcartprice = shopcartprice;
    }
}
