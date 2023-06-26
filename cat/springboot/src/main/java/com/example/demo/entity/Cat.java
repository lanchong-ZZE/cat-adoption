package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("cat")
public class Cat {

    @TableId(type = IdType.AUTO)
    private Integer catid;
    private String catimg;
    private String cattype;
    private String catage;
    private String catinfo;
    private String state;

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatimg() {
        return catimg;
    }

    public void setCatimg(String catimg) {
        this.catimg = catimg;
    }

    public String getCattype() {
        return cattype;
    }

    public void setCattype(String cattype) {
        this.cattype = cattype;
    }

    public String getCatage() {
        return catage;
    }

    public void setCatage(String catage) {
        this.catage = catage;
    }

    public String getCatinfo() {
        return catinfo;
    }

    public void setCatinfo(String catinfo) {
        this.catinfo = catinfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
