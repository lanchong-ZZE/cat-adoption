package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("adopt")
public class Adopt {

    @TableId(type = IdType.AUTO)
    private Integer adoptid;
    private Integer userid;
    private Integer catid;
    private String adoptimg;
    private String adoptstate;

    public Integer getAdoptid() {
        return adoptid;
    }

    public void setAdoptid(Integer adoptid) {
        this.adoptid = adoptid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getAdoptimg() {
        return adoptimg;
    }

    public void setAdoptimg(String adoptimg) {
        this.adoptimg = adoptimg;
    }

    public String getAdoptstate() {
        return adoptstate;
    }

    public void setAdoptstate(String adoptstate) {
        this.adoptstate = adoptstate;
    }
}
