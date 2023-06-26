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
      <el-table-column prop="catid" label="小猫ID"></el-table-column>
      <el-table-column label="小猫图片">
        <template slot-scope="scope">
          <el-image style="width:100px; height:130px; border-radius: 10px" :src="scope.row.adoptimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="adoptstate" label="申请领养状态"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_adopt_history",
  data(){
    return{
      tableData:[],
      form: {},
    }
  },
  created() {
    this.get_adopt_history()
  },
  methods:{
    get_adopt_history(){
      request.get("/api/admin/show_adopt_history").then(res => {
        this.tableData = res.data;
        for(let i = 0; i < res.data.length; i++){
          if(res.data[i].adoptstate === '0'){
            this.tableData[i].adoptstate = '未通过'
          }
          else if(res.data[i].adoptstate === '1'){
            this.tableData[i].adoptstate = '申请中'
          }
          else if(res.data[i].adoptstate === '2'){
            this.tableData[i].adoptstate = '已领养'
          }
        }
        console.log(this.tableData)
      })
    },
  }
}
</script>

<style scoped>

</style>