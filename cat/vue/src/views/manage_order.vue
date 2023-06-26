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
      <el-table-column label="历史订单商品图">
        <template slot-scope="scope">
          <el-image style="width:100px; height:120px;" :src="scope.row.ordersimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="ordersname" label="历史订单商品名称"></el-table-column>
      <el-table-column prop="ordersnum" label="历史订单商品数量"></el-table-column>
      <el-table-column prop="ordersprice" label="历史订单商品价格"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_order",
  data(){
    return{
      tableData:[],
      form: {},
    }
  },
  created() {
    this.get_orders()
  },
  methods:{
    get_orders(){
      request.get("/api/admin/show_orders").then(res => {
        this.tableData = res.data;
      })
    },
  }
}
</script>

<style scoped>

</style>