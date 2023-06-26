<template>
  <div class="left-menu">
    <h2>个人主页</h2>
    <router-link to="personal_info" class="menu"><img src="../assets/我的资料.png">我的资料</router-link>
    <router-link to="personal_post" class="menu"><img src="../assets/我的喵圈.png">我的喵圈</router-link>
    <router-link to="personal_adopt" class="menu"><img src="../assets/领养记录.png">领养记录</router-link>
    <router-link to="personal_cart" class="menu"><img  src="../assets/购物车.png">购物车</router-link>
    <router-link to="personal_order" class="menu"><img src="../assets/历史订单.png">历史订单</router-link>
  </div>
</template>

<script>
export default {
  name: "personal_leftside",
  data() {
    return {

    }
  },
  methods:{

  }
}
</script>

<style scoped>
  .left-menu{
    min-width: 228.36px;
    position: fixed;
    left: 20%;
    display: flex;
    flex-direction: column;
    width: 12%;
    height: calc(100vh - 80px);
    background: rgba(10,10,10,0.3);
    color: white;
    padding: 20px 20px 20px 20px;
    line-height: 50px;
    font-size: larger;
    z-index: 3;
  }
  h2{
    text-align: center;
    margin-bottom: 20px;
    border-bottom: 1px solid cornflowerblue;
  }
  .menu{
    padding-left: 10px;
    color: white;
    text-decoration: none;
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }
  .menu:hover{
    background-color: rgba(255,255,255,0.2);
  }
  .menu:focus{
    font-weight: bolder;
    color: #41B883;
  }
  img{
    width:30px;
    height:30px;
    margin-right: 20px;
  }
</style>