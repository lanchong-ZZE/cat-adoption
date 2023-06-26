<template>
  <div class="aside">
    <el-menu
        router="true"
        style="height: calc(100vh - 80px);"
        default-active="2"
        class="el-menu-vertical-demo">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>领养小猫管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="manage_adopt"><i class="el-icon-tickets"></i>小猫管理</el-menu-item>
          <el-menu-item index="manage_apply" class="apply">
            <div v-if="s === '1'" class="point"></div>
            <i class="el-icon-tickets"></i>领养申请审批</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="manage_goods">
        <i class="el-icon-tickets"></i>
        <span slot="title">小猫用品管理</span>
      </el-menu-item>
      <el-menu-item index="manage_post">
        <i class="el-icon-tickets"></i>
        <span slot="title">喵圈管理</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>用户管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="manage_users"><i class="el-icon-tickets"></i>用户资料管理</el-menu-item>
          <el-menu-item index="manage_adopt_history"><i class="el-icon-tickets"></i>领养记录管理</el-menu-item>
          <el-menu-item index="manage_order"><i class="el-icon-tickets"></i>历史订单管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <img class="logo" src="../assets/logo.png">
  </div>
</template>

<script>

export default {
  name: "manage_aside",
  data(){
    return{
      s: sessionStorage.getItem('value')
    }
  },
  created() {

  },
  methods:{

  }
}
</script>

<style scoped>
  .aside{
    width: 15%;
    position: relative;
    overflow: hidden;
  }
  .logo{
    position: absolute;
    width: 90%;
    bottom: 5%;
    left: 50%;
    transform: translateX(-50%);
  }
  .apply{
    position: relative;
  }
  .point{
    position: absolute;
    left: 25px;
    top: 20px;
    width: 12px;
    height: 12px;
    border-radius: 6px;
    background-color: red;
  }
  ::v-deep .el-submenu__title{
    font-size: 22px;
  }
  ::v-deep .el-menu-item{
    font-size: 22px;

  }
</style>