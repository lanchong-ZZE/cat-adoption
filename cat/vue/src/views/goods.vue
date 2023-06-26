<template>
  <div class="page">
    <div class="main">
      <div class="goods" v-for="item in goodsList">
        <div @click="buy(item.goodsid,item.goodsimg,item.goodsname,item.goodsprice,item.goodsinfo)">
          <div class="goods_img_div"><img class="goods_img" :src=item.goodsimg></div>
          <div class="goods_text">{{item.goodsname}}</div>
          <div class="goods_text">¥：{{item.goodsprice}}</div>
          <div class="goods_text">详情介绍：{{item.goodsinfo}}</div>
        </div>
        <div class="goods_btn_bg">
          <button class="goods_btn" @click="add_cart(item.goodsid,item.goodsimg,item.goodsname,item.goodsprice)">加入购物车</button>
          <button class="goods_btn" @click="buy(item.goodsid,item.goodsimg,item.goodsname,item.goodsprice,item.goodsinfo)">立即购买</button>
        </div>
      </div>
      <el-dialog :close-on-click-modal="false" title="🐱 小猫用品" :visible.sync="dialogVisible" width="60%">
        <div class="goods_info">
              <img class="goods_info_img" :src=goodsInfo.img>
              <div class="goods_info_name">{{goodsInfo.name}}</div>
              <div class="goods_info_price">
                <div class="goods_info_price_sign">¥</div>
                <div class="goods_info_price_digit">{{goodsInfo.price}}</div>
                <div class="goods_info_price_num">
                  <button class="goods_info_price_num_button" @click="reduce(num,goodsInfo.price)">-</button>
                  <div style="width: 50px;">{{num}}</div>
                  <button class="goods_info_price_num_button" @click="add(num,goodsInfo.price)">+</button>
                </div>
              </div>
              <div class="goods_info_details">详情介绍：{{goodsInfo.info}}</div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">返 回</el-button>
          <el-button type="primary" @click="pay(goodsInfo.id,goodsInfo.img,goodsInfo.name,num,goodsInfo.price)">下 单</el-button>
        </span>
      </el-dialog>
    </div>
    <div class="shopcart">
      <div style="width: 100px;height: 100px;display: flex;justify-content:center;align-items:center;">
        <router-link to="personal_cart"><img style="width: 100px;height: 100px" src="../assets/shopcart.png"></router-link>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "goods",
  data() {
    return {
      goodsList: {},
      goodsInfo: {},
      form: {},
      dialogVisible: false,
      num: 1,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/api/goods/show_goods").then(res => {
        this.goodsList = res.data;
        console.log(this.goodsList)
      })
    },
    add_cart(id,img,name,price){
      this.form={
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
        goodsid: id,
        shopcartimg: img,
        shopcartname: name,
        shopcartprice: price
      }
      request.post("/api/goods/find_shopcart",this.form).then(res=>{
        if(res.data === null){
          request.post("/api/goods/add_shopcart",this.form).then(res=>{
            if(res.code === '0') {
              this.$message({
                type: "success",
                message: "已加入购物车",
                duration: 1500
              })
              console.log(res)
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
        else {
          this.$confirm('购物车已存在该商品, 是否再次加入?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'info'
          }).then(() => {
            this.form={
              shopcartid: res.data.shopcartid,
              userid: JSON.parse(sessionStorage.getItem('user_info')).id,
              goodsid: id,
              shopcartimg: img,
              shopcartname: name,
              shopcartnum: res.data.shopcartnum + 1,
              shopcartprice: price * (res.data.shopcartnum + 1)
            }
            request.put("/api/goods/add_again_shopcart",this.form).then(res =>{
              console.log(res)
              if(res.code === '0') {
                this.$message({
                  type: "success",
                  message: "已再次加入购物车",
                  duration: 1500
                })
              }else{
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          }).catch(() => {})
        }
      })
    },
    buy(id,img,name,price,info){
      this.num = 1
      request({
        url:'/api/user/show_userinfo',
        method:'get',
        params:{
          username: JSON.parse(sessionStorage.getItem('user_info')).username,
        }
      }).then(res=>{
        if(res.data.address === null||res.data.address === ''||res.data.phone === null||res.data.phone === ''){
          this.$confirm('个人信息未填写完毕，请先前往"个人中心-我的资料"进行完善', '提示', {
            confirmButtonText: '前往',
            cancelButtonText: '取消',
            type: 'info'
          }).then(()=>{
            this.$router.push("/personal_info")
          }).catch(() => {})
        }else {
          this.goodsInfo={
            userid: JSON.parse(sessionStorage.getItem('user_info')).id,
            id: id,
            img: img,
            name: name,
            price: price,
            info: info
          }
          this.dialogVisible = true
        }
      })
    },
    reduce(num, price){
      if(num > 1){
        this.num = num - 1
        this.goodsInfo.price = price / num * (num - 1)
      }
      else{
        this.$message({
          type: "info",
          message: "该用品已无法减少",
          duration: 1500
        })
      }
    },
    add(num, price){
      this.num = num + 1
      this.goodsInfo.price = price / num * (num + 1)
    },
    pay(id,img,name,num,price){
      this.$confirm('是否确认下单', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'info'
      }).then(()=>{
        this.form = {
          userid: JSON.parse(sessionStorage.getItem('user_info')).id,
          goodsid: id,
          ordersimg: img,
          ordersname: name,
          ordersnum: num,
          ordersprice: price
        }
        request.post("/api/goods/add_orders",this.form).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "购买成功",
              duration: 1500
            })
            this.dialogVisible = false
            console.log(res)
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
  .page{
    display: flex;
    flex-wrap: wrap;
    min-width: 1143px;
    width: 100vm;
    background: -webkit-linear-gradient(#162050, #B4B4DC);
    background: -o-linear-gradient(#162050, #B4B4DC);
    background: -moz-linear-gradient(#162050, #B4B4DC);
    background: linear-gradient(#162050, #B4B4DC);
    background-attachment: fixed;
    padding-top: 80px;
  }
  .main{
    min-width: 1143px;
    min-height: calc(100vh - 80px);
    width: 60%;
    margin: 0 auto;
    background: rgba(10,10,10,0.3);
    display: flex;
    flex-wrap: wrap;
    padding: 3px 0px 0px 10px;
  }
  .goods{
    width: 24.6%;
    height: 425px;
    margin-right: 3px;
    margin-bottom: 3px;
    padding: 3px 3px 3px 3px;
    background: rgba(10,10,10,0.3);
  }
  .goods_img_div{
    width: 100%;
    height: 260px;
    position: relative;
    margin-bottom: 10px;
  }
  .goods_img{
    position: absolute;
    width: 260px;
    height: 260px;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
    border-radius: 10px;
  }
  /*.goods_img{*/
  /*  margin-left: 10px;*/
  /*  width: 95%;*/
  /*  height: 260px;*/
  /*  border-radius: 20px;*/
  /*  background-color: #c9d5e0;*/
  /*  box-shadow: 30px 30px 30px -10px  rgba(0,0,0,0.15),*/
  /*              inset 15px 15px 10px rgba(255,255,255,0.75),*/
  /*              -15px -15px 35px rgba(255,255,255,0.55),*/
  /*              inset -1px -1px 10px rgba(0,0,0,0.2);*/
  /*}*/
  .goods_text{
    color: rgb(220,220,220);
    padding-left: 5px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .goods_btn_bg{
    text-align: center;
  }
  .goods_btn{
    width: 94%;
    height: 35px;
    margin-top: 7px;
    background: rgba(200,200,200,0.3);
    border: none;
    border-radius: 10px;
    font-size: 15px;
    font-weight: bolder;
    border: 1px solid #242222;
  }
  .goods_btn:hover{
    background: rgba(250,250,250,0.5);
    box-shadow: 5px 5px 9px rgb(250,250,250);
    border: 1px solid white;
  }
  .goods_btn:active{
    transform: scale(0.9,0.9);
    box-shadow: 5px 5px 9px rgb(250,250,250);
  }
  .goods_info{
    width: 100%;
    height: 400px;
    display: grid;
    gap: 10px;
    grid-template-columns: 400px auto;
    grid-template-rows: 100px 100px 100px 100px;
    grid-template-areas: 'a b'
                         'a c'
                         'a d'
                         'a d';
  }
  .goods_info_img{
    grid-area: a;
    width: 100%;
    height: 100%;
    border-radius: 20px;
    border: 2px solid rgb(200,200,200);
  }
  .goods_info_name{
    grid-area: b;
    background-color: rgba(200,200,200,0.1);
    font-size: xx-large;
    color: black;
    font-weight: bolder;
    padding: 10px;
    overflow: auto;
  }
  .goods_info_price{
    grid-area: c;
    background-color: rgba(200,200,200,0.1);
    display: flex;
    color: cornflowerblue;
    padding: 10px;
    position: relative;
  }
  .goods_info_price_sign{
    padding-top: 14px;
    font-weight: bolder;
  }
  .goods_info_price_digit{
    margin-left: 5px;
    font-size: xx-large;
  }
  .goods_info_price_num{
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    position: absolute;
    bottom: 0px;
    color: black;
    font-size: x-large;
  }
  .goods_info_price_num_button{
    width: 21px;
    height: 21px;
    font-size: large;
  }
  .goods_info_details{
    grid-area: d;
    background-color: rgba(200,200,200,0.1);
    padding: 10px;
    overflow: auto;
    font-size: 20px;
  }
  .shopcart{
    position: fixed;
    width: 100px;
    height: 100px;
    margin-top: 30px;
    right: 70px;
    border-radius: 40px;
    background-color: #c9d5e0;
    box-shadow: 30px 30px 30px -10px  rgba(0,0,0,0.6),
    inset 15px 15px 10px rgba(255,255,255,0.75),
    inset -1px -1px 10px rgba(0,0,0,0.2);
  }
  .shopcart:hover{
    box-shadow: 30px 30px 30px -10px  rgba(0,0,0,0.6),
    inset 15px 15px 10px rgba(255,255,255,0.75),
    -6px -6px 20px rgba(255,255,255,0.55),
    inset -1px -1px 10px rgba(0,0,0,0.2);
  }
  .shopcart:active{
    transform: scale(0.9,0.9);
  }
</style>