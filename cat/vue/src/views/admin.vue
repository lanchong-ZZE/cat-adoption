<template>
  <div class="admin">
    <Manage_header/>
    <Manager_aside/>
    <router-view class="router-view" />
  </div>
</template>

<script>
import Manage_header from "@/views/manage_header";
import Manager_aside from "@/views/manage_aside";

export default {
  name: "admin",
  components: {Manage_header,Manager_aside},
  data(){
    return{

    }
  }
}
</script>

<style scoped>
  .admin{
    font-family: 微软雅黑;
    display: flex;
    flex-wrap: wrap;
  }
  .router-view{
    width: 85%;
    padding: 20px;
  }
  ::v-deep .el-button{
    margin: 0 10px 10px 0;
    font-size: 22px;
    font-weight: bold;
  }
  ::v-deep .el-table{
    font-size: 22px;
  }
</style>