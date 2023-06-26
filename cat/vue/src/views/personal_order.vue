<template>
  <div class="main">
    <div class="orders" v-for="item in ordersList">
      <div class="space"></div>
      <img :src="item.ordersimg">
      <div class="name">{{item.ordersname}}</div>
      <div class="num">数量：×{{item.ordersnum}}<div>实付款：¥{{item.ordersprice}}</div></div>
      <div class="add_cart">
        <button @click="add_cart(item.goodsid,item.ordersimg,item.ordersname,item.ordersnum,item.ordersprice)">加入购物车</button>
      </div>
    </div>
    <div class="bottom_text">😊亲，已经到底了哦～</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "personal_order",
  data() {
    return {
      form:{},
      ordersList:{}
    }
  },
  created() {
    this.show_orders()
  },
  methods:{
    show_orders(){
      this.form = {
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
      }
      request.post("/api/goods/show_orders",this.form).then(res => {
        console.log(res)
        this.ordersList = res.data;
      })
    },
    add_cart(id,img,name,num,price){
      this.form={
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
        goodsid: id,
        shopcartimg: img,
        shopcartname: name,
        shopcartnum: num,
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
              shopcartnum: res.data.shopcartnum + num,
              shopcartprice: res.data.shopcartprice + price
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
    }
  }
}
</script>

<style scoped>
  .main{
    width: 80%;
    min-height: calc(100vh - 80px);
    background: #ececec;
    margin-left: 20%;
    padding: 10px;
  }
  .orders{
    width: 100%;
    height: 170px;
    background-color: rgb(180,180,180);
    margin-bottom: 10px;
    border-radius: 100px;
    border: 5px solid rgb(50,50,50);
    display: grid;
    gap: 2%;
    grid-template-columns: 7% 17% 50% 20%;
    grid-template-rows: 49% 49%;
    grid-template-areas: 'a b c d'
                         'a b e d';
  }
  .space{
    grid-area: a;
  }
  img{
    grid-area: b;
    width: 150px;
    height: 150px;
    margin: auto;
    border-radius: 20px;
    border: 3px solid rgb(200,200,200);
  }
  .name{
    grid-area: c;
    margin: auto 0;
    font-size: x-large;
    font-weight: bolder;
  }
  .num{
    grid-area: e;
    margin: auto 0;
    font-size: large;
    font-weight: bolder;
  }
  .add_cart{
    grid-area: d;
    margin: auto;
  }
  button{
    width: 150px;
    height: 50px;
    border-radius: 50px;
    border: 4px solid rgb(100,100,100);
    font-size: 18px;
    font-weight: bolder;
  }
  button:hover{
    border: 3px solid rgb(200,200,200);
  }
  button:active{
    transform: scale(0.95,0.95);
  }
  .bottom_text{
    text-align: center;
    font-family: 微软雅黑;
    font-weight: bold;
    font-size: 15px;
    color: rgb(100,100,100);
  }
</style>