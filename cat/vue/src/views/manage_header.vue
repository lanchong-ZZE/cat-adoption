<template>
  <div class="top">
    <div class="title">养猫人后台管理</div>
    <div style="flex: 1"></div>
    <img @click="exit()" class="exit-img" src="../assets/exit.png">
  </div>
</template>

<script>
export default {
  name: "manage_header",
  data(){
    return{

    }
  },
  methods:{
    exit() {
      this.$confirm('是否退出登录', '提示', {
        confirmButtonText: '退出',
        cancelButtonText: '返回',
        type: 'info'
      }).then(() => {
        sessionStorage.clear();
        this.$router.push("/")
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>
  .top {
    width: 100vw;
    height: 80px;
    border-bottom: 1px solid #ccc;
    display: flex;
    align-items: center;
    padding: 0 30px;
    font-size: 30px;
  }
  .title{
    color: dodgerblue;
    font-weight: bold;
  }
  .exit-img {
    width: 50px;
    height: 50px;
  }
  .exit-img:active{
    transform: scale(0.9,0.9);
  }
</style>