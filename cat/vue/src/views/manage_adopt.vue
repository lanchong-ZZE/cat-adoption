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
      <el-table-column prop="catid" label="猫咪ID" width="100px"></el-table-column>
      <el-table-column label="猫咪图片">
        <template slot-scope="scope">
          <el-image style="width:170px; height:200px" :src="scope.row.catimg"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="cattype" label="猫咪品种"></el-table-column>
      <el-table-column prop="catage" label="猫咪年龄"></el-table-column>
      <el-table-column prop="catinfo" label="猫咪介绍"></el-table-column>
      <el-table-column prop="state" label="领养状态" width="150px"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" slot="reference" @click="del(scope.row.catid)">删除</el-button>
          <el-dialog
              title="新增"
              :visible.sync="dialogVisible_add"
              width="30%">
            <el-form :model="form" label-width="120px">
              <el-form-item label="猫咪图片：">
                <el-upload
                    v-model="form.catimg"
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
              <el-form-item label="猫咪品种：">
                <el-input v-model="form.cattype" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="猫咪年龄：">
                <el-input v-model="form.catage" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="猫咪介绍：">
                <el-input type="textarea" v-model="form.catinfo" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="领养状态：">
                <el-radio v-model="form.state" label="0">可申请</el-radio>
                <el-radio v-model="form.state" label="1">申请中</el-radio>
                <el-radio v-model="form.state" label="2">已领养</el-radio>
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
              <el-form-item label="猫咪图片：">
                <el-upload
                    v-model="form.catimg"
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
              <el-form-item label="猫咪品种：">
                <el-input v-model="form.cattype" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="猫咪年龄：">
                <el-input v-model="form.catage" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="猫咪介绍：">
                <el-input type="textarea" v-model="form.catinfo" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="领养状态：">
                <el-radio v-model="form.state" label="0">可申请</el-radio>
                <el-radio v-model="form.state" label="1">申请中</el-radio>
                <el-radio v-model="form.state" label="2">已领养</el-radio>
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
  name: "manage_adopt",
  data(){
    return{
      tableData: [],
      form: {},
      dialogVisible_add: false,
      dialogVisible_edit: false,
      row_id: '',
    }
  },
  created() {
    this.get_adopt()
  },
  methods:{
    get_adopt(){
      request.get("/api/goods/show_cat").then(res => {
        this.tableData = res.data;
        for(let i = 0; i < res.data.length; i++){
          if(res.data[i].state === '0'){
            this.tableData[i].state = '可申请'
          }
          else if(res.data[i].state === '1'){
            this.tableData[i].state = '申请中'
          }
          else if(res.data[i].state === '2'){
            this.tableData[i].state = '已领养'
          }
        }
        console.log(this.tableData)
      })
    },
    edit(row){
      this.row_id = row.catid
      this.form = JSON.parse(JSON.stringify(row))
      if(this.form.state === '可申请'){
        this.form.state = '0'
      }
      if(this.form.state === '申请中'){
        this.form.state = '1'
      }
      if(this.form.state === '已领养'){
        this.form.state = '2'
      }
      this.dialogVisible_edit = true
    },
    update(form){
      let obj = {catid: this.row_id}
      Object.assign(form,obj)
      console.log("更新",form)
      this.dialogVisible_edit = false
      request.put("/api/admin/update_cat",form).then(res =>{
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功",
            duration: 1500
          })
          this.get_adopt()
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
      if(form.catimg === undefined || form.cattype === undefined || form.catage === undefined || form.catinfo === undefined || form.state === undefined || form.cattype === '' || form.catage === '' || form.catinfo === ''){
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
          request.post("/api/admin/add_cat",form).then(res=>{
            if(res.code === '0') {
              this.$message({
                type: "success",
                message: "添加成功",
                duration: 1500
              })
              this.dialogVisible_add = false
              this.get_adopt()
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
          url:'/api/admin/delete_cat',
          method:'delete',
          params:{
            catid:id
          }
        }).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "删除成功",
              duration: 1000
            })
            this.get_adopt()
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
      this.form.catimg = res.data
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