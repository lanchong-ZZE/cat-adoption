<template>
  <div class="main">
    <div class="post" v-for="item in postList">
      <div class="post_left">
        <img class="user_headimg" :src="item.headimg">
      </div>
      <div class="post_right">
        <div class="post_text">{{item.posttext}}</div>
        <div class="post_imgs">
          <div v-for="i in item.postimg">
            <img class="post_img" :src="i.data">
          </div>
        </div>
      </div>
      <button class="delete" @click="del(item.postid)">删除</button>
    </div>
    <div class="bottom_text">😊亲，已经到底了哦～</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "personal_post",
  data(){
    return {
      form:{},
      postList:{},
    }
  },
  created() {
    this.show_personal_post()
  },
  methods:{
    show_personal_post(){
      this.form = {
        userid: JSON.parse(sessionStorage.getItem('user_info')).id,
      }
      request.post("/api/goods/show_personal_post",this.form).then(res => {
        for( let i = 0; i < res.data.length; i++){
          res.data[i]['postimg'] = JSON.parse(res.data[i]['postimg'])
        }
        this.postList = res.data;
        console.log(res.data)
      })
    },
    del(id){
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request({
          url:'/api/goods/delete_personal_post',
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
            this.show_personal_post()
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
  .main{
    width: 80%;
    min-height: calc(100vh - 80px);
    background: #ececec;
    margin-left: 20%;
    padding: 20px 20px 0px 20px;
  }
  .post{
    margin-left: 5%;
    margin-right: 5%;
    width: 90%;
    margin-bottom: 10px;
    padding: 10px;
    display: flex;
    border-bottom: 1px solid rgb(100,100,100);
    position: relative;
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
  .bottom_text{
    text-align: center;
    font-family: 微软雅黑;
    font-weight: bold;
    font-size: 15px;
    color: rgb(100,100,100);
  }
  .delete{
    position: absolute;
    right: 0;
    bottom: 5px;
    color: gray;
    font-family: 微软雅黑;
    border: none;
    font-size: 15px;
  }
  .delete:hover{
    color: black;
    font-weight: bold;
  }
</style>