<template>
  <div>
    <el-table
        height="calc(100vh - 120px)"
        :row-style="{height: 40}"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column prop="id" label="用户ID"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column label="用户头像">
        <template slot-scope="scope">
          <el-image style="width:100px; height:100px; border-radius: 50px" :src="scope.row.headimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" slot="reference" @click="del(scope.row.id)">注销用户</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_users",
  data(){
    return{
      tableData:[],
      form: {},
    }
  },
  created() {
    this.get_users()
  },
  methods:{
    get_users(){
      request.get("/api/admin/show_users").then(res => {
        this.tableData = res.data;
      })
    },
    del(id){
      this.$confirm('是否确认注销该用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request({
          url:'/api/admin/delete_users',
          method:'delete',
          params:{
            id:id
          }
        }).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "注销成功",
              duration: 1000
            })
            this.get_users()
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }).catch(() => {})
    },
  }
}
</script>

<style scoped>

</style>