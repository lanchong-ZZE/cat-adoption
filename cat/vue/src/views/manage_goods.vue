<template>
  <div>
    <el-button type="primary" @click="add()">新增</el-button>
    <el-table
        height="calc(100vh - 178px)"
        :row-style="{height: 40}"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column prop="goodsid" label="小猫用品ID" width="130px"></el-table-column>
      <el-table-column label="小猫用品图片">
        <template slot-scope="scope">
          <el-image style="width:170px; height:200px" :src="scope.row.goodsimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="goodsname" label="小猫用品名称"></el-table-column>
      <el-table-column prop="goodsprice" label="小猫用品价格"></el-table-column>
      <el-table-column prop="goodsinfo" label="小猫用品介绍"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" slot="reference" @click="del(scope.row.goodsid)">删除</el-button>
          <el-dialog
              title="新增"
              :visible.sync="dialogVisible_add"
              width="30%">
            <el-form :model="form" label-width="120px">
              <el-form-item label="小猫用品图片：">
                <el-upload
                    v-model="form.goodsimg"
                    action="http://localhost:9090/files/upload"
                    list-type="picture-card"
                    :on-success="filesUploadSuccess"
                    :on-error="filesUploadError"
                    :limit="1"
                    :on-exceed="handleExceed"
                    ref="upload">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="小猫用品名称：">
                <el-input v-model="form.goodsname" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="小猫用品价格：">
                <el-input v-model="form.goodsprice" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="小猫用品介绍：">
                <el-input type="textarea" v-model="form.goodsinfo" style="width: 80%"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible_add = false">取 消</el-button>
              <el-button type="primary" @click="insert(form)">确 定</el-button>
            </span>
          </el-dialog>
          <el-dialog
              title="更新"
              :visible.sync="dialogVisible_edit"
              width="30%">
            <el-form :model="form" label-width="120px">
              <el-form-item label="小猫用品图片：">
                <el-upload
                    v-model="form.goodsimg"
                    action="http://localhost:9090/files/upload"
                    list-type="picture-card"
                    :on-success="filesUploadSuccess"
                    :on-error="filesUploadError"
                    :limit="1"
                    :on-exceed="handleExceed"
                    ref="upload">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="小猫用品名称：">
                <el-input v-model="form.goodsname" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="小猫用品价格：">
                <el-input v-model="form.goodsprice" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="小猫用品介绍：">
                <el-input type="textarea" v-model="form.goodsinfo" style="width: 80%"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible_edit = false">取 消</el-button>
              <el-button type="primary" @click="update(form)">更新</el-button>
            </span>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "manage_goods",
  data(){
    return{
      tableData:[],
      form: {},
      dialogVisible_add: false,
      dialogVisible_edit: false,
      row_id: '',
    }
  },
  created() {
    this.get_goods()
  },
  methods:{
    get_goods(){
      request.get("/api/goods/show_goods").then(res => {
        this.tableData = res.data;
      })
    },
    edit(row){
      this.row_id = row.goodsid
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible_edit = true
    },
    update(form){
      let obj = {goodsid: this.row_id}
      Object.assign(form,obj)
      console.log("更新",form)
      this.dialogVisible_edit = false
      request.put("/api/admin/update_goods",form).then(res =>{
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功",
            duration: 1500
          })
          this.get_goods()
        }else{
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    add(){
      this.dialogVisible_add = true
      this.form = {}
    },
    insert(form){
      if(form.goodsimg === undefined || form.goodsname === undefined || form.goodsprice === undefined || form.goodsinfo === undefined || form.goodsinfo === '' || form.goodsname === '' || form.goodsprice === ''){
        this.$message({
          type: "warning",
          message: "请填写完成所有内容",
          duration: 1500
        })
      }
      else {
        this.$confirm('是否确认添加', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'info'
        }).then(()=>{
          request.post("/api/admin/add_goods",form).then(res=>{
            if(res.code === '0') {
              this.$message({
                type: "success",
                message: "添加成功",
                duration: 1500
              })
              this.dialogVisible_add = false
              this.get_goods()
              console.log(res)
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }).catch(() => {})
      }
    },
    del(id){
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request({
          url:'/api/admin/delete_goods',
          method:'delete',
          params:{
            goodsid:id
          }
        }).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "删除成功",
              duration: 1000
            })
            this.get_goods()
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }).catch(() => {})
    },
    filesUploadSuccess(res) {
      console.log(res)
      this.form.goodsimg = res.data
    },
    filesUploadError(){
      this.$message({
        type: "error",
        message: '上传失败（注：只能上传jpg/png文件，且不超过500kb）',
        duration: 2500
      })
    },
    handleExceed(){
      this.$message({
        type: "warning",
        message: '最多上传1个文件，请删除已添加文件，再重新上传',
        duration: 2500
      })
    },
  }
}
</script>

<style scoped>

</style>