<template>
  <div>
    <el-table
        height="calc(100vh - 120px)"
        :row-style="{height: 40}"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column>用户领养申请：</el-table-column>
      <el-table-column prop="userid" label="用户ID"></el-table-column>
      <el-table-column prop="catid" label="小猫ID"></el-table-column>
      <el-table-column label="小猫图片">
        <template slot-scope="scope">
          <el-image style="width:100px; height:130px; border-radius: 10px" :src="scope.row.adoptimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="审批">
        <template slot-scope="scope">
          <el-popconfirm title="是否确认该申请未通过" @confirm="Y_confirm(scope.row)" @cancel="Y_cancel">
            <el-button type="danger" slot="reference">未通过</el-button>
          </el-popconfirm>
          <el-popconfirm title="是否确认该申请通过" @confirm="N_confirm(scope.row)" @cancel="N_cancel">
            <el-button type="primary" slot="reference">已通过</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_apply",
  data(){
    return{
      tableData:[],
      form: {},
    }
  },
  created() {
    this.get_apply()
  },
  methods:{
    get_apply(){
      this.form = {
        adoptstate: '1'
      }
      request.post("/api/admin/show_apply",this.form).then(res => {
        this.tableData = res.data;
        if(res.data.length !== 0){
          sessionStorage.setItem("value",'1')
        }else {
          sessionStorage.setItem("value",'0')
        }
        console.log("申请数据：",res.data)
      })
    },
    Y_confirm(row){
      this.form = {
        adoptid: row.adoptid,
        adoptstate : '2'
      }
      request.put("/api/admin/update_apply",this.form).then(res=>{
        if(res.code === '0') {
          this.form = {
            catid : row.catid,
            state : '0'
          }
          request.put("/api/goods/update_cat_state",this.form).then(res=>{
            if(res.code === '0') {
              this.$message({
                type: "success",
                message: "审批成功",
                duration: 1000
              })
              this.get_apply()
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      }).catch(() => {})
    },
    Y_cancel(){},
    N_confirm(row){
      this.form = {
        adoptid: row.adoptid,
        adoptstate : '0'
      }
      request.put("/api/admin/update_apply",this.form).then(res=>{
        if(res.code === '0') {
          this.form = {
            catid : row.catid,
            state : '2'
          }
          request.put("/api/goods/update_cat_state",this.form).then(res=>{
            if(res.code === '0') {
              this.$message({
                type: "success",
                message: "审批成功",
                duration: 1000
              })
              this.get_apply()
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      }).catch(() => {})
    },
    N_cancel(){}
  }
}
</script>

<style scoped>

</style>