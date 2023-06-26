<template>
  <div class="top">
    <div class="logo-img"><img class="logo-img" src="../assets/logo.png"></div>
    <div style="flex: 1"></div>
    <div class="menu">
      <router-link to="index" class="text-menu">首页</router-link>
      <router-link to="adopt" class="text-menu">领养小猫</router-link>
      <router-link to="goods" class="text-menu">小猫用品</router-link>
      <router-link to="post" class="text-menu">喵圈</router-link>
      <router-link to="personal" class="text-menu">个人中心</router-link>
    </div>
    <div class="exit"><img @click="exit()" class="exit-img" src="../assets/exit.png"></div>
  </div>
</template>

<script>
export default {
  name: "header",
  data() {
    return {

    }
  },
  methods:{
    exit(){
      this.$confirm('是否退出登录', '提示', {
        confirmButtonText: '退出',
        cancelButtonText: '返回',
        type: 'info'
      }).then(() => {
        sessionStorage.clear();
        this.$router.push("/")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
  .top{
    position: fixed;
    z-index: 4;
    padding-left: 20%;
    padding-right: 20%;
    width: 100%;
    height: 80px;
    line-height: 80px;
    border-bottom: 1px solid rgb(200,200,200);
    display: flex;
    /*background-color: #162050;*/
    background-color: rgb(22,32,80);
    font-size: larger;
    font-weight: bold;
  }
  .logo-img{
    width: 170px;
    height: 70px;
    margin-top: 2px;
    margin-left: 10px;
  }
  .menu{
    padding-right: 20px;
    min-width: 578px;
  }
  .text-menu{
    height: 100px;
    color: #e9e9e9;
    text-decoration: none;
    padding-left: 50px;
  }
  .text-menu:hover{
    text-decoration-line: underline;
    text-decoration-style: wavy;
    text-decoration-color: cornflowerblue;
  }
  .text-menu:focus{
    text-decoration-line: underline;
    text-decoration-style: wavy;
    text-decoration-color: cornflowerblue;
  }
  .exit{
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    right: 0px;
    background-color: rgb(22,32,80);
    border-bottom: 1px solid rgb(200,200,200);
  }
  .exit-img{
    width: 40px;
    height: 40px;
  }
  .exit-img:hover{
    opacity: 0.6;
  }
  .exit-img:active{
    transform: scale(0.9,0.9);
  }
</style>