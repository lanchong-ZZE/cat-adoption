<template>
  <div class="main">
    <div class="p_info_div">
      <div class="up">
        <div class="bg">
          <img class="bg_img" :src="bgimg">
          <div class="bg_update" @click="dialogVisible_bg_img = true">
            <img class="bg_update_img" src="../assets/换封面.png">
            <div class="bg_update_text">换封面</div>
          </div>
        </div>
        <div class="up_bottom">
          <div class="head" @click="user_img">
            <img class="head_img" :src="headimg">
          </div>
          <div class="up_bottom_data">
            <div class="nickname_null" v-if="nickname===''" @click="edit">[请编辑你的昵称]</div>
            <div class="nickname">{{nickname}}</div>
            <div class="username">用户名：{{username}}</div>
          </div>
          <div class="edit_div">
            <img class="edit_img" src="../assets/edit.png" @click="edit">
          </div>
        </div>
      </div>
      <div class="down">
        <div class="down_data">
          <div class="info">
            <div class="info_left">年龄:</div><div class="info_right">{{age}}</div>
          </div>
          <div class="info">
            <div class="info_left">性别:</div><div class="info_right">{{sex}}</div>
          </div>
          <div class="info">
            <div class="info_left">电话:</div><div class="info_right">{{phone}}</div>
          </div>
          <div class="info">
            <div class="info_left">地址:</div><div class="info_right">{{address}}</div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog :close-on-click-modal="false"
               :show-close="false"
               title="🎴‍ 更换封面"
               :visible.sync="dialogVisible_bg_img"
               width="400px"
               top="15%">
      <el-upload
          action="http://localhost:9090/files/upload"
          list-type="picture"
          :on-success="filesUploadSuccess_bgimg"
          :on-error="filesUploadError"
          :limit="1"
          :on-exceed="handleExceed"
          ref="upload">
        <el-button type="primary">点击上传</el-button>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="update_bgimg">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :close-on-click-modal="false"
               :show-close="false"
               title="😶‍ 更换头像"
               :visible.sync="dialogVisible_user_img"
               width="400px"
               top="15%">
      <el-upload
          action="http://localhost:9090/files/upload"
          list-type="picture-card"
          :on-success="filesUploadSuccess_headimg"
          :on-error="filesUploadError"
          :limit="1"
          :on-exceed="handleExceed"
          ref="upload">
        <i class="el-icon-plus"></i>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="update_headimg">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :close-on-click-modal="false"
               title="📝 编辑资料"
               :visible.sync="dialogVisible"
               width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>
    <div class="p_info_right_div"></div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "personal_info",
  data(){
    return{
      id:'',
      age:'',
      sex:'',
      phone:'',
      address:'',
      nickname:'',
      username:'',
      bgimg:'',
      headimg:'',
      form:{},
      url_headimg:'',
      url_bgimg:'',
      dialogVisible: false,
      dialogVisible_user_img: false,
      dialogVisible_bg_img: false,
    }
  },
  created() {
    this.load()
    this.show_userinfo()
  },
  methods:{
    load(){
      this.username = JSON.parse(sessionStorage.getItem('user_info')).username
    },
    show_userinfo(){
      request({
        url:'/api/user/show_userinfo',
        method:'get',
        params:{
          username:this.username
        }
        // request.get("/api/user/show_userinfo",
        //     {
        //       params:{
        //         username:this.username
        //       }
        //     }).then(res => {
        //   console.log(res)
        // })
      }).then(res=>{
        sessionStorage.setItem("user_info",JSON.stringify(res.data))
        console.log(res)
        this.age = res.data.age
        this.sex = res.data.sex
        this.phone = res.data.phone
        this.address = res.data.address
        this.id=res.data.id
        this.nickname = res.data.nickname
        this.bgimg = res.data.bgimg
        this.headimg = res.data.headimg
      })
    },
    user_img(){
      this.dialogVisible_user_img = true
    },
    filesUploadSuccess_headimg(res) {
      console.log(res)
      this.url_headimg = res.data
    },
    filesUploadSuccess_bgimg(res) {
      console.log(res)
      this.url_bgimg = res.data
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
    cancel(){
      this.dialogVisible_user_img = false
      this.dialogVisible_bg_img = false
      this.$refs['upload'].clearFiles()
    },
    update_headimg(){
      this.form = {
        id: this.id,
        headimg: this.url_headimg
      }
      request.put("/api/user/update",this.form).then(res =>{
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "更换头像成功",
            duration: 1500
          })
          this.show_userinfo()
        }else{
          this.$message({
            type: "error",
            message: res.msg,
            duration: 1500
          })
        }
        this.dialogVisible_user_img = false
        this.$refs['upload'].clearFiles()
      })
    },
    update_bgimg(){
      this.form = {
        id: this.id,
        bgimg: this.url_bgimg
      }
      request.put("/api/user/update",this.form).then(res =>{
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "更换封面成功",
            duration: 1500
          })
          this.show_userinfo()
        }else{
          this.$message({
            type: "error",
            message: res.msg,
            duration: 1500
          })
        }
        this.dialogVisible_bg_img = false
        this.$refs['upload'].clearFiles()
      })
    },
    edit(){
      this.form = {
        id: this.id,
        username: this.username,
        nickname:this.nickname,
        age: this.age,
        sex: this.sex,
        phone: this.phone,
        address: this.address
      }
      this.dialogVisible = true
    },
    update(){
      request.put("/api/user/update",this.form).then(res =>{
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功",
            duration: 1500
          })
          this.show_userinfo()
        }else{
          this.$message({
            type: "error",
            message: res.msg,
            duration: 1500
          })
        }
        this.dialogVisible = false
      })
    }
  }
}
</script>

<style scoped>
  .main{
    display: flex;
    width: 79%;
    min-height: calc(100vh - 80px);
    margin-left: 21%;
  }
  .p_info_div{
    width: 72%;
    background: #ececec;
  }
  .p_info_right_div{
    width: 27%;
    margin-left: 1%;
    background: rgba(10,10,10,0.3);
  }
  .up{
    display: flex;
    flex-wrap: wrap;
    width: 100%;
  }
  .up_bottom{
    display: flex;
    width: 100%;
    height: 100px;
  }
  .bg{
    width: 100%;
    height: 350px;
    position: relative;
    overflow: hidden;
  }
  .bg:hover .bg_update{
    transform: translateX(-103%);
    transition: 0.5s;
  }
  .bg_img{
    width: 100%;
    height: 350px;
  }
  .bg_update{
    width: 40px;
    display: flex;
    flex-direction: column;
    position: absolute;
    top: 1px;
    right: -40px;
    border-radius: 5px;
    transition: 0.3s;
  }
  .bg_update_text{
    font-size: 13px;
    font-weight: bold;
    color: #3366cb;
  }
  .bg_update_img{
    width: 40px;
    height: 30px;
    background-color: rgba(255,255,255,0.4);
    border-radius: 5px;
  }
  .head{
    display: flex;
    flex-direction: column;
    width: 100px;
    height: 100px;
    border-radius: 50px;
    position: relative;
    margin-top: -50px;
    margin-left: 15%;
    box-shadow: 0px 0px 20px 5px rgb(100,149,237);
  }
  .head_img{
    width: 100px;
    height: 100px;
    border-radius: 50px;
  }
  .up_bottom_data{
    width: 50%;
    height: 100%;
    margin-left: 20px;
  }
  .nickname_null{
    width: 100%;
    height: 0;
    color: rgba(100,100,100,0.5);
    line-height: 60px;
  }
  .nickname{
    width: 100%;
    height: 50%;
    font-size: xx-large;
    font-weight: bold;
    color: cornflowerblue;
    padding-top: 5px;
  }
  .username{
    width: 100%;
    height: 50%;
    color: cornflowerblue;
    padding-top: 10px;
    padding-left: 30px;
  }
  .edit_div{
    width: 16%;
    height: 100%;
  }
  .edit_img{
    float: right;
    width: 40px;
    height: 40px;
  }
  .edit_img:hover{
    transform: scale(1.1,1.1);
  }
  .edit_img:active{
    transform: scale(0.8,0.8);
  }
  .down{
    width: 100%;
    display: flex;
    justify-content: center;
  }
  .down_data{
    margin-top: 30px;
    width: 300px;
  }
  .info{
    display: flex;
    height: 50px;
    color: cornflowerblue;
  }
  .info_left{
    width: 70px;
    font-size: large;
    font-weight: bold;
    padding-top: 25px;
  }
  .info_right{
    overflow-x: scroll;
    white-space: nowrap;
    width: 230px;
    padding-top: 25px;
    padding-left: 5px;
    border-bottom: 1px solid cornflowerblue;
  }
  ::-webkit-scrollbar {
    width: 0;
    height: 0;
    color: transparent;
  }
</style>