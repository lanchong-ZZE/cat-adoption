<template>
  <div class="page">
    <div class="main">
      <div class="cat" v-for="item in catList">
        <img class="cat_img" :src="item.catimg">
        <div class="cat_type">品种：{{item.cattype}}</div>
        <div class="cat_age">猫龄：{{item.catage}}</div>
        <div class="cat_info" @click="show_info(item.catinfo)">详细介绍：{{item.catinfo}}<span>(点击查看)</span></div>
        <div class="cat_button">
          <button class="btn1" v-if="item.state == 0" @click="add_adopt(item.catid,item.catimg,item.cattype,item.catage,item.catinfo,item.state)">申请领养</button>
          <button class="btn2" v-if="item.state == 1">🚫暂不可再次申请</button>
          <button class="btn3" v-if="item.state == 2">✔️已被领养</button>
          <div class="state">
            <img v-if="item.state == 0" height="100%" src="../assets/state_green.png">
            <img v-if="item.state == 1" height="100%" src="../assets/state_yellow.png">
            <img v-if="item.state == 2" height="100%" src="../assets/state_red.png">
            <div v-if="item.state == 0" class="state_tips">可申请</div>
            <div v-if="item.state == 1" class="state_tips">申请中</div>
            <div v-if="item.state == 2" class="state_tips">已领养</div>
          </div>
        </div>
      </div>

      <el-dialog title="📋 详细介绍" :visible.sync="dialogVisible" width="30%" top="15%">
        <div class="show_info">{{info}}</div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "adopt",
  data(){
    return {
      catList:{},
      form:{},
      dialogVisible: false,
      info:''
    }
  },
  created() {
    this.show_cat()
  },
  methods:{
    show_cat(){
      request.get("/api/goods/show_cat").then(res => {
        this.catList = res.data;
      })
    },
    show_info(info){
      this.info = info
      this.dialogVisible = true
    },
    add_adopt(id,img){
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
        }
        else {
          this.form={
            userid: JSON.parse(sessionStorage.getItem('user_info')).id,
            catid: id,
            adoptimg: img,
            adoptstate: 1
          }
          request({
            url:'/api/goods/find_adopt',
            methods: 'get',
            params:{
              userid:this.form.userid
            }
          }).then(res=>{
            console.log(res.data)
            if(res.data === null){
              request.post("/api/goods/add_adopt",this.form).then(res=>{
                if(res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "申请成功，请等待审批",
                    duration: 2000
                  })
                  this.form = {
                    catid: id,
                    state: 1
                  }
                  request.put("/api/goods/update_cat_state",this.form).then(res=>{
                    this.show_cat()
                  })
                }else{
                  this.$message({
                    type: "error",
                    message: res.msg
                  })
                }
              })
            }
            else{
              this.$message({
                type: "error",
                message: "您已有领养申请处于审批中，等待审批完成后才可再次操作",
                duration: 2300
              })
            }
          })
        }
      })
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
  min-height: calc(100vh - 80px);
  background: -webkit-linear-gradient(#162050, #B4B4DC);
  background: -o-linear-gradient(#162050, #B4B4DC);
  background: -moz-linear-gradient(#162050, #B4B4DC);
  background: linear-gradient(#162050, #B4B4DC);
  background-attachment: fixed;
  padding-top: 80px;
}
  .main{
    min-width: 1143px;
    width: 60%;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    background: rgba(10,10,10,0.3);
    padding: 3px 0px 0px 10px;
  }
  .cat{
    width: 24.6%;
    height: 470px;
    margin-bottom: 3px;
    padding: 10px;
    background: rgba(10,10,10,0.3);
    display: grid;
    grid-template-columns: 50% 50%;
    grid-template-rows: 73% 9% 9% 9%;
    grid-template-areas: 'a a'
                         'b c'
                         'd d'
                         'e e';
  }
  .cat_img{
    width: 100%;
    height: 100%;
    padding: 5px;
    grid-area: a;
    background-color: rgb(170,170,170);
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }
  .cat_type{
    grid-area: b;
    border: 4px solid rgb(170,170,170);
    border-top: none;
    border-right: 2px solid rgb(170,170,170);
    background-color: rgb(50,50,75);
    color: white;
    line-height: 35px;
    overflow: hidden;
    white-space: nowrap;
  }
  .cat_age{
    grid-area: c;
    border: 4px solid rgb(170,170,170);
    border-top: none;
    border-left: 2px solid rgb(170,170,170);
    background-color: rgb(50,50,75);
    color: white;
    line-height: 35px;
    overflow: hidden;
    white-space: nowrap;
  }
  .cat_info{
    grid-area: d;
    border: 4px solid rgb(170,170,170);
    border-top: none;
    background-color: rgb(50,50,75);
    color: white;
    line-height: 35px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    position: relative;
    padding-right: 50px;
  }
  span{
    position: absolute;
    right: 0;
    font-size: x-small;
    font-family: 微软雅黑;
    color: cornflowerblue;
  }
  .cat_button{
    grid-area: e;
    border: 4px solid rgb(170,170,170);
    border-top: none;
    background-color: rgb(50,50,75);
    display: flex;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
  }
  .btn1{
    width: 84%;
    height: 100%;
    background-color: #41B883;
    font-weight: bold;
    border-radius: 20px;
  }
  .btn1:hover{
    background-color: #4fe5a2;
  }
  .btn1:active{
    transform: scale(0.97,0.9);
  }
  .btn2{
    width: 84%;
    height: 100%;
    background-color: #f4ea2a;
    font-weight: bold;
    border-radius: 20px;
  }
  .btn3{
    width: 84%;
    height: 100%;
    background-color: #d81e06;
    font-weight: bold;
    border-radius: 20px;
  }
  .state{
    width: 16%;
    height: 100%;
    background-color: rgb(50,50,75);
    border-left: 4px solid rgb(170,170,170);
    position: relative;
    overflow: hidden;
    border-bottom-right-radius: 10px;
  }
  .state:hover{
    overflow: visible;
  }
  .state_tips{
    width: 50px;
    height: 25px;
    position: absolute;
    line-height: 25px;
    font-weight: bolder;
    background-color: white;
    margin-top: 5px;
    box-shadow: 3px 3px #404040;
    z-index: 99;
  }
  .show_info{
    width: 100%;
    background-color: #e2e2e2;
    font-size: 20px;
  }
</style>