<template>
  <div class="main">
    <div class="top">
      <div class="checkall">
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      </div>
      <div style="flex: 1"></div>合计:
      <div class="top_all_price">
        <div></div>
        <div class="top_all_price_digit">{{ all_price }}</div>
      </div>
      <button @click="pay">下单</button>
    </div>
    <div style="margin: 80px"></div>
    <el-checkbox-group v-model="option" @change="handleCheckedCitiesChange">
      <div class="card" v-for="item in shopcartList">
      <div class="select_div">
        <el-checkbox :label="item.shopcartid"><br/></el-checkbox>
      </div>
      <img class="delete_div" src="../assets/delete.png" @click="del(item.shopcartid)">
      <div class="shopcartimg_div">
        <img class="shopcartimg" :src="item.shopcartimg">
      </div>
      <div class="card_right">
        <div class="card_right_up">
          <div class="card_right_up_name">
           {{item.shopcartname}}
          </div>
        </div>
        <div class="card_right_down">
          <div class="card_right_down_price">
            <div class="card_right_down_price_sign">￥</div>
            <div class="card_right_down_price_digit">
              ¥{{item.shopcartprice}}
            </div>
          </div>
          <div class="card_right_down_num">
            <button class="card_right_down_num_button" @click="reduce(item.shopcartid,item.shopcartnum,item.shopcartprice)">-</button>
            <div style="width: 50px">{{item.shopcartnum}}</div>
            <button class="card_right_down_num_button" @click="add(item.shopcartid,item.shopcartnum,item.shopcartprice)">+</button>
          </div>
        </div>
      </div>
    </div>
    </el-checkbox-group>
    <div class="bottom_text">😊亲，已经到底了哦～</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "personal_cart",
  data() {
    return {
      shopcartList: [],
      form: {},
      option:[],
      checkAll: false,
      isIndeterminate: true,
      IDList:[],
      all_price: 0,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.form = {
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
      }
      request.post("/api/goods/show_shopcart",this.form).then(res => {
        this.IDList=[];
        this.shopcartList = res.data;
        this.shopcartList.forEach(item=>this.IDList.push(item.shopcartid));
        this.get_all_price();
      })
    },
    handleCheckAllChange(val) {
      this.option = val ? this.IDList : [];
      this.isIndeterminate = false;
      this.get_all_price()
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.IDList.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.IDList.length;
      this.get_all_price()
    },
    get_select_item(){ // 获取选中商品的所有数据对象
      let ans=[];
      for(let i=0;i<this.shopcartList.length;i++) {
        let item = this.shopcartList[i];
        if (this.option.includes(item.shopcartid)) {
          ans.push(item);
        }
      }
      return ans;
    },
    get_all_price(){ // 获取选中商品的总价格
      let money = 0;
      for(let i=0;i<this.shopcartList.length;i++) {
        let item = this.shopcartList[i];
        if (this.option.includes(item.shopcartid)) {
          money = this.accAdd(money,item.shopcartprice);
        }
      }
      this.all_price = money;
    },
    accAdd(arg1,arg2){ // 解决浮点数加法精度丢失
      let r1,r2,m;
      try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
      try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
      m=Math.pow(10,Math.max(r1,r2));
      return (arg1*m+arg2*m)/m;
    },
    pay(){
      if(this.option.length === 0){
        this.$message({
          type: "warning",
          message: '未勾选任何商品'
        })
      }else {
        this.$confirm('是否确认下单', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'info'
        }).then(()=>{
          for(let i=0;i<this.option.length;i++) {
            this.form = {
              userid: JSON.parse(sessionStorage.getItem('user_info')).id,
              goodsid: this.get_select_item()[i].goodsid,
              ordersimg: this.get_select_item()[i].shopcartimg,
              ordersname: this.get_select_item()[i].shopcartname,
              ordersnum: this.get_select_item()[i].shopcartnum,
              ordersprice: this.get_select_item()[i].shopcartprice
            }
            request.post("/api/goods/add_orders",this.form).then(()=>{
              request({
                url:'/api/goods/delete_shopcart',
                method:'delete',
                params:{
                  shopcartid:this.get_select_item()[i].shopcartid
                }
              }).then(()=>{
                this.load()
              }).catch(() => {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              })
            }).catch(() => {
              this.$message({
                type: "error",
                message: res.msg
              })
            })
          }
          this.$message({
            type: "success",
            message: "下单成功"
          })
        }).catch(() => {})
      }
    },
    del(id){
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request({
          url:'/api/goods/delete_shopcart',
          method:'delete',
          params:{
            shopcartid:id
          }
        }).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "删除成功",
              duration: 1000
            })
            this.load()
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }).catch(() => {})
    },
    reduce(id,num,price){
      if(num > 1){
        this.form={
          shopcartid: id,
          shopcartnum: num - 1,
          shopcartprice: price / num * (num - 1)
        }
        request.put("/api/goods/add_again_shopcart",this.form).then(() =>{
          this.load()
        })
      }
      else{
        this.$message({
          type: "info",
          message: "该用品已无法减少",
          duration: 1000
        })
      }
    },
    add(id,num,price){
      this.form={
        shopcartid: id,
        shopcartnum: num + 1,
        shopcartprice: price / num * (num + 1)
      }
      request.put("/api/goods/add_again_shopcart",this.form).then(res =>{
        this.load()
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
    padding: 0px 20px 0px 20px;
  }
  .top{
    width: 914px;
    height: 60px;
    background-color: #ececec;
    overflow: hidden;
    position: fixed;
    margin-left: -20px;
    display: flex;
    align-items: center;
    z-index: 2;
    border-bottom: 6px solid cornflowerblue;
    font-weight: bold;
  }
  .checkall{
    margin-top: -4px;
  }
  .top_all_price{
    display: flex;
    align-items: center;
    position: relative;
    color: #5499ff;
  }
  .top_all_price_digit{
    font-size: xx-large;
    margin-left: 4px;
  }
  .top button{
    width: 80px;
    height: 40px;
    margin: 0 10px;
    font-size: 20px;
    font-family: 楷体;
    font-weight: bold;
    color: white;
    background-color: #2e73ee;
    border: none;
    border-radius: 15px;
  }
  .top button:hover{
    background-color: #3f86ff;
  }
  .top button:active{
    transform: scale(0.95,0.95);
  }
  .card{
    display: flex;
    position: relative;
    width: 100%;
    height: 300px;
    background: #ececec;
    border-radius: 20px;
    box-shadow: 0 0 15px 1px rgb(200,200,200);
    margin-bottom: 30px;
    transition: 0.1s ease-in;
  }
  .card:hover{
    box-shadow: 0px 0px 25px 2px rgb(100,149,237);
    transform: scale(1.02,1.02);
    transition: 0.1s ease-in;
  }
  .select_div{
    width: 6%;
    height: 300px;
    display: flex;
    align-items: center;
  }
  .delete_div{
    position: absolute;
    top: 10px;
    right: 10px;
    width: 30px;
    height: 30px;
  }
  .delete_div:hover{
    opacity: 0.7;
  }
  .delete_div:active{
    transform: scale(0.7,0.7);
  }
  .shopcartimg_div{
    width: 35%;
    height: 300px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .shopcartimg{
    width: 90%;
    height: 90%;
    border-radius: 20px;
  }
  .card_right{
    display: flex;
    flex-direction: column;
    width: 59%;
    height: 300px;
  }
  .card_right_up{
    width: 100%;
    height: 200px;
  }
  .card_right_up_name{
    margin-top: 70px;
    margin-left: 20px;
    font-size: 30px;
    font-weight: bolder;
    overflow: auto;
  }
  .card_right_down{
    width: 100%;
    height: 100px;
    position: relative;
  }
  .card_right_down_price{
    position: absolute;
    left: 0;
    bottom: 30px;
    color: cornflowerblue;
  }
  .card_right_down_price_sign{
    padding-top: 11px;
    font-weight: bolder;
  }
  .card_right_down_price_digit{
    margin-left: 5px;
    font-size: xx-large;
  }
  .card_right_down_num{
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    position: absolute;
    right: 30px;
    bottom: 30px;
    font-size: x-large;
  }
  .card_right_down_num_button{
    width: 21px;
    height: 21px;
    font-size: large;
  }
  .bottom_text{
    text-align: center;
    font-family: 微软雅黑;
    font-weight: bold;
    font-size: 15px;
    color: rgb(100,100,100);
  }
  ::v-deep .el-checkbox__inner {
    transform: scale(3,3);
    border-radius: 20px;
    margin-left: 25px;
  }
  ::v-deep .el-checkbox__label {
    font-size: 20px;
    position: relative;
    left: 10px;
    top: 4px;
    z-index: 1;
    color: cornflowerblue;
    font-weight: bold;
  }
</style>