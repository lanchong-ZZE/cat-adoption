<template>
  <div>
    <el-table
        height="calc(100vh - 120px)"
        :row-style="{height: 40}"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column prop="userid" label="用户ID"></el-table-column>
      <el-table-column label="用户头像">
        <template slot-scope="scope">
          <el-image style="width:100px; height:100px; border-radius: 50px" :src="scope.row.headimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="posttext" label="内容文本"></el-table-column>
      <el-table-column label="内容图片">
        <template slot-scope="scope">
          <div v-for="item in scope.row.postimg">
            <el-image style="width:200px; height:200px" :src="item.data"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" slot="reference" @click="del(scope.row.postid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_post",
  data(){
    return{
      tableData:[],
      form: {},
    }
  },
  created() {
    this.get_post()
  },
  methods:{
    get_post(){
      request.get("/api/goods/show_post").then(res => {
        for( let i = 0; i < res.data.length; i++){
          res.data[i]['postimg'] = JSON.parse(res.data[i]['postimg'])
        }
        this.tableData = res.data;
      })
    },
    del(id){
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request({
          url:'/api/admin/delete_post',
          method:'delete',
          params:{
            postid:id
          }
        }).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "删除成功",
              duration: 1000
            })
            this.get_post()
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