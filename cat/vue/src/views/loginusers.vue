<template>
  <div class="body">
    <div>
      <img id="logo" src="../assets/logo_black.png">
    </div>
    <div class="logo">养猫人</div>
    <div class="login">
      <div class="top">用户登录</div>
      <el-form ref="form" :model="form">
        <el-form-item style="margin: 0">
          <input placeholder="输入用户名" v-model="form.username">
        </el-form-item>
        <el-form-item style="margin: 0">
          <input type="password" placeholder="输入密码" v-model="form.password">
        </el-form-item>
        <el-button @click="login">登录</el-button>
      </el-form>
        <div id="down" class="down"><router-link to="loginadmin">管理员登录</router-link></div>
        <div id="reg" class="reg"><router-link to="register">未拥有账号，前往注册</router-link></div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "loginusers",
  data(){
    return{
      form:{},
    }
  },
  created() {

  },
  mounted() {
    history.pushState(null, null, document.URL)
    window.addEventListener('popstate', function() {
      history.pushState(null, null, document.URL)
    })
  },
  methods:{
    login(){
      if(this.form.username === ''||this.form.username === undefined){
        this.$message({
          type: "error",
          message: "请输入用户名"
        })
      }
      else {
        if(this.form.password === ''||this.form.password === undefined){
          this.$message({
            type: "error",
            message: "请输入密码"
          })
        }
        else{
          request.post("/api/user/loginusers",this.form).then(res =>{
            if(res.code === '0'){
              this.$message({
                type: "success",
                message: "登录成功",
                duration: 1000
              })
              sessionStorage.setItem("user_info",JSON.stringify(res.data))
              this.$router.push("/cat&people")
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
</script>

<style scoped>
  .body {
    background-image: url("../assets/bg.png");
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
    background: rgba(240, 248, 255, 0.2);
    border-radius: 10px;
    box-shadow: 5px 6px 10px black;
  }

  .login {
    position: absolute;
    right: 0;
    text-align: center;
    width: 450px;
    height: 400px;
    background: rgba(240, 248, 255, 0.3);
    margin: 150px 3% 3% 70%;
    border-radius: 20px;
  }

  .logo {
    width: 100px;
    height: 70px;
    position: absolute;
    margin-left: 220px;
    margin-top: 10px;
    background: rgba(240, 248, 255, 0.2);
    border-radius: 10px;
    font-size: x-large;
    text-align: center;
    line-height: 70px;
    font-weight: 700;
    box-shadow: 5px 6px 10px black;
  }

  input {
    width: 400px;
    height: 50px;
    background: rgba(255, 255, 255, 0.4);
    font-size: x-large;
    border-radius: 10px;
    margin-top: 20px;
    padding-left: 5px;
    box-shadow: 5px 6px 10px #3e3e3e;
  }

  button {
    width: 400px;
    height: 50px;
    border-radius: 10px;
    margin-top: 30px;
    background: #5499ff;
    border: 1px solid #3c70ff;
    box-shadow: 5px 6px 10px black;
    font-size: x-large;
    font-family: 楷体;
    font-weight: bolder;
    color: black;
  }

  button:hover {
    background: #1e5eff;
    color: black;
    border: none;
  }

  button:active {
    opacity: 0.7;
    transform: scale(0.9, 0.9);
  }
  button:focus {
    background: #1e5eff;
    color: black;
    border: none;
  }
  .top {
    margin-left: 10px;
    font-size: xx-large;
    font-weight: 700;
    width: 430px;
    height: 70px;
    line-height: 70px;
    border-bottom: 1px solid #3c70ff;
  }

  .down {
    float: left;
    margin-top: 80px;
    margin-left: 20px;
  }

  #down a:link,
  #down a:visited {
    color: black;
    text-decoration: none;
  }

  #down a:hover,
  #down a:active {
    color: black;
    text-decoration: underline;
  }

  .reg {
    float: right;
    margin-top: 80px;
    margin-right: 20px;
    color: #224dff;
  }

  #reg a:link,
  #reg a:visited {
    color: #224dff;
    text-decoration: none;
  }

  #reg a:hover,
  #reg a:active {
    color: #224dff;
    text-decoration: underline;
  }
</style>