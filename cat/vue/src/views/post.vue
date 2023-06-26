<template>
  <div class="page">
    <div class="main">
      <div class="space"></div>
      <div class="bg">
        <div class="data">
          <div class="personal">
            <img class="personal_img" :src="headimg">
            <div class="personal_nickname">{{nickname}}</div>
            <div class="write" @click="dialogVisible = true">
              <img class="write_img" src="../assets/write.png">发布
            </div>
          </div>
          <div class="post" v-for="item in postList">
            <div class="post_left">
              <img class="user_headimg" :src="item.headimg">
            </div>
            <div class="post_right">
              <div class="post_text">{{item.posttext}}</div>
              <div class="post_imgs" v-if="item.postimg !== null && item.postimg !== ''">
                <div v-for="i in item.postimg">
                  <img class="post_img" :src="i.data">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="space"></div>
      <el-dialog :close-on-click-modal="false"
                 title="💬 发布喵圈"
                 :visible.sync="dialogVisible"
                 width="30%">
        <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6}"
            placeholder="请输入内容"
            resize="none"
            v-model="textarea">
        </el-input>
        <div style="margin: 20px"></div>
        <el-upload
            action="http://localhost:9090/files/upload"
            list-type="picture-card"
            :on-success="filesUploadSuccess"
            :limit="6"
            :on-exceed="handleExceed">
          <i class="el-icon-plus"></i>
        </el-upload>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="add_post(textarea)">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "post",
  data() {
    return {
      dialogVisible: false,
      textarea: '',
      form: {},
      postList: '',
      list: [],
      headimg:'',
      nickname:'',
      bool: false,
    }
  },
  created() {
    this.show_post()
  },
  methods:{
    show_post(){
      this.headimg = JSON.parse(sessionStorage.getItem('user_info')).headimg
      this.nickname = JSON.parse(sessionStorage.getItem('user_info')).nickname
      request.get("/api/goods/show_post").then(res => {
        for( let i = 0; i < res.data.length; i++){
          res.data[i]['postimg'] = JSON.parse(res.data[i]['postimg'])
        }
        this.postList = res.data;
      })
    },
    add_post(textarea){
      if(textarea === '' && this.list === ''){
        this.$message({
          type: "warning",
          message: "内容不能为空！",
          duration: 2000
        })
      }
      else {
        this.form = {
          userid: JSON.parse(sessionStorage.getItem('user_info')).id,
          headimg: JSON.parse(sessionStorage.getItem('user_info')).headimg,
          posttext: textarea,
          postimg: JSON.stringify(this.list)
        }
        request.post("/api/goods/add_post",this.form).then(res=>{
          if(res.code === '0') {
            this.$message({
              type: "success",
              message: "发布成功",
              duration: 1500
            })
            this.dialogVisible = false
            this.show_post()
            console.log(res)
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
    },
    filesUploadSuccess(res){
      this.bool = true
      this.list.push(res)
      console.log(this.list)
    },
    handleExceed(){
      this.$message({
        type: "warning",
        message: '最多上传6张图片！',
        duration: 2500
      })
    },
  }
}
</script>

<style scoped>
  .page{
    display: flex;
    flex-wrap: wrap;
    min-width: 1143px;
    width: 100vm;
    background: -webkit-linear-gradient(#162050, #B4B4DC);
    background: -o-linear-gradient(#162050, #B4B4DC);
    background: -moz-linear-gradient(#162050, #B4B4DC);
    background: linear-gradient(#162050, #B4B4DC);
    background-attachment: fixed;
    padding-top: 80px;
  }
  .main{
    min-width: 1143px;
    width: 60%;
    min-height: calc(100vh - 80px);
    margin: 0 auto;
    background: rgba(10,10,10,0.3);
    display: flex;
    flex-wrap: wrap;
    padding: 3px 0px 0px 10px;
  }
  .space{
    flex: 1;
  }
  .bg{
    width: 70%;
    background-image: url("../assets/post_img.png");
    background-size:contain;
  }
  .data{
    margin-top: 300px;
    width: 98%;
    min-height: calc(100vh - 383px);
    background-color: white;
    border-top-left-radius: 60px;
    border-top-right-radius: 60px;
    margin-left: 1%;
    margin-right: 1%;
    box-shadow: 0px 0px 15px 6px rgb(110,110,110);
    padding: 20px;
  }
  .personal{
    width: 100%;
    height: 150px;
    display: flex;
    position: relative;
    margin-bottom: 50px;
    border-bottom: 1px solid cornflowerblue;
  }
  .personal:hover{
    border-bottom:  10px solid #7aa3f3;
    transition: 1.5s;
  }
  .personal_img{
    width: 100px;
    height: 100px;
    border-radius: 50px;
    margin-top: 20px;
  }
  .personal_nickname{
    margin: 30px 30px;
    font-size: 40px;
    font-weight: bold;
    color: cornflowerblue;
  }
  .write{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    height: 30px;
    position: absolute;
    right: 0;
    top: 50px;
    font-size: 20px;
    font-weight: bold;
    border: 2px solid #5c5c5c;
    border-radius: 15px;
    box-shadow: 5px 8px 7px black;
  }
  .write:hover{
    transform: scale(1.05,1.05);
  }
  .write:active{
    transform: translate(5%,5%);
  }
  .write_img{
    width: 30px;
    height: 30px;
  }
  .post{
    margin-left: 5%;
    margin-right: 5%;
    width: 90%;
    margin-bottom: 10px;
    padding: 10px;
    display: flex;
    border-bottom: 1px solid rgb(100,100,100);
  }
  .post_left{
    width: 20%;
    margin-right: 10px;
    text-align: center;
  }
  .user_headimg{
    width: 70px;
    height: 70px;
    border-radius: 35px;
  }
  .post_right{
    width: 78%;
    display: flex;
    flex-direction: column;
    padding-top: 23px;
  }
  .post_text{
    width: 100%;
    margin-bottom: 10px;
    font-weight: bold;
    font-size: 20px;
  }
  .post_imgs{
    display: flex;
    flex-wrap: wrap;
  }
  .post_img{
    width: 200px;
    height: 200px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px solid black;
  }
</style>
