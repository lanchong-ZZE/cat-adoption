<template>
  <div class="main">
    <div class="adopt" v-for="item in adoptList">
      <img class="adopt_img" :src="item.adoptimg">
      <div class="adopt_state">
        领养申请状态：
        <div v-if="item.adoptstate == 0">已通过</div>
        <div v-if="item.adoptstate == 1">审批中</div>
        <div v-if="item.adoptstate == 2">未通过</div>
      </div>
      <img v-if="item.adoptstate == 0" class="state_img" src="../assets/state_green.png">
      <img v-if="item.adoptstate == 1" class="state_img" src="../assets/state_yellow.png">
      <img v-if="item.adoptstate == 2" class="state_img" src="../assets/state_red.png">
    </div>
    <div class="bottom_text">😊亲，已经到底了哦～</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "personal_adopt",
  data() {
    return {
      form:{},
      adoptList:{}
    }
  },
  created() {
    this.show_adopt()
  },
  methods:{
    show_adopt(){
      this.form = {
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
      }
      request.post("/api/goods/show_adopt",this.form).then(res => {
        console.log(res)
        this.adoptList = res.data;
      })
    }
  }
}
</script>

<style scoped>
  .main{
    width: 80%;
    height: calc(100vh - 80px);
    background: #ececec;
    margin-left: 20%;
    padding: 10px;
  }
  .adopt{
    width: 100%;
    height: 170px;
    background-color: rgb(180,180,180);
    margin-bottom: 10px;
    border-radius: 100px;
    border: 5px solid rgb(50,50,50);
    display: flex;
    padding: 0 8% 0 8%;
  }
  .adopt_img{
    width: 20%;
    height: 100%;
    margin: 0 0 0 0;
    border-radius: 20px;
    border: 3px solid rgb(200,200,200);
  }
  .adopt_state{
    flex: 1;
    width: 40%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 30px;
    font-weight: bold;
  }
  .state_img{
    width: 60px;
    height: 60px;
    margin: auto;
  }
  .bottom_text{
    text-align: center;
    font-family: 微软雅黑;
    font-weight: bold;
    font-size: 15px;
    color: rgb(100,100,100);
  }
</style>