<template>
  <div class="body">
    <div>
      <img id="logo" src="../assets/logo_black.png">
    </div>
    <div class="logo">养猫人</div>
    <div class="login">
      <div class="top">用户注册</div>
      <el-form ref="form" :model="form">
        <el-form-item style="margin: 0">
          <input placeholder="输入用户名" v-model="form.username">
        </el-form-item>
        <el-form-item style="margin: 0">
          <input type="password" placeholder="输入密码" v-model="form.password">
        </el-form-item>
        <el-form-item style="margin: 0">
          <input type="password" placeholder="确认密码" v-model="form.confirm">
        </el-form-item>
          <el-button @click="register">注册</el-button>
      </el-form>
        <div id="back" class="back"><router-link to="loginusers">返回用户登录界面</router-link></div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "register",
  data(){
    return{
      form:{}
    }
  },
  methods:{
    register(){
      if(this.form.username === ''||this.form.username === undefined){
        this.$message({
          type: "error",
          message: "请输入用户名"
        })
      }
      else{
        if(this.form.password === ''||this.form.password === undefined){
          this.$message({
            type: "error",
            message: "请输入密码"
          })
        }
        else {
          if(this.form.confirm === ''||this.form.confirm === undefined){
            this.$message({
              type: "error",
              message: "请再次确认密码"
            })
          }
          else {
            if(this.form.password !== this.form.confirm){
              this.$message({
                type: "error",
                message: "两次密码输入不一致"
              })
            }
            else {
              request.post("/api/user/register",this.form).then(res =>{
                if(res.code === '0'){
                  this.$message({
                    type: "success",
                    message: "注册成功"
                  })
                  this.$router.push("/")
                }
                else{
                  this.$message({
                    type: "error",
                    message: res.msg
                  })
                }
              })
            }
          }
        }
      }
    }
  }
}
</script>

<style scoped>
  .body{
    background-image: url("../assets/bg.png");
    width: 100vm;
    height: 100vh;
    background-repeat: no-repeat;
    background-size: cover;
    font-family: 楷体;
    font-weight: bolder;
    position: relative;
  }
  #logo {
    position: absolute;
    margin-left: 10px;
    margin-top: 10px;
    padding: 5px 20px 5px 20px;
    width: 200px;
    height: 70px;
    background: rgba(240,248,255,0.2);
    border-radius: 10px;
    box-shadow: 5px 6px 10px black;
  }
  .login{
    position: absolute;
    right: 0;
    text-align: center;
    width: 450px;
    height: 400px;
    background: rgba(240,248,255,0.3);
    margin: 150px 3% 3% 70%;
    border-radius: 20px;
  }
  .logo{
    width: 100px;
    height: 70px;
    position: absolute;
    margin-left: 220px;
    margin-top: 10px;
    background: rgba(240,248,255,0.2);
    border-radius: 10px;
    font-size: x-large;
    text-align: center;
    line-height: 70px;
    font-weight: 700;
    box-shadow: 5px 6px 10px black;
  }
  input{
    width: 400px;
    height: 50px;
    background: rgba(255,255,255,0.4);
    font-size: x-large;
    border-radius: 10px;
    margin-top: 20px;
    padding-left: 5px;
    box-shadow: 5px 6px 10px #3e3e3e;
  }
  button{
    width: 400px;
    height: 50px;
    border-radius: 10px;
    margin-top: 20px;
    background: #db883f;
    border: 1px solid #b46a39;
    box-shadow: 5px 6px 10px black;
    font-size: x-large;
    font-family: 楷体;
    font-weight: bolder;
    color: black;
  }
  button:hover{
    background: #b4561d;
    color: black;
    border: none;
  }
  button:active{
    opacity: 0.7;
    transform: scale(0.9,0.9);
  }
  button:focus {
    background: #b4561d;
    color: black;
    border: none;
  }
  .top{
    margin-left: 10px;
    font-size: xx-large;
    font-weight: 700;
    width: 430px;
    height: 70px;
    line-height: 70px;
    border-bottom: 1px solid #db883f;
  }
  .back{
    float: right;
    margin-top: 20px;
    margin-right: 20px;
  }
  #back a:link,
  #back a:visited{
    color: black;
    text-decoration:none;
  }
  #back a:hover,
  #back a:active{
    color: #ff5f2c;
    text-decoration:none;
  }
</style>