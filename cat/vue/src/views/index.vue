<template>
  <div class="page">
    <div class="main">
      <el-carousel :interval="5000" type="card" height="350px"style="width: 100%">
        <el-carousel-item v-for="item in imgList" :key="item">
          <img class="show-img" :src=item>
        </el-carousel-item>
      </el-carousel>
      <div class="cat_div">
        <div class="cat_info_div">
          <div class="cat_img_div">
            <img class="cat_img" src="../assets/缅因猫.png">
          </div>
          <el-popover
              placement="bottom"
              width="350"
              trigger="hover"
              content="因原产于美国缅因州而得名，是北美自然产生的第一个长毛猫品种，约于18世纪中叶形成较稳定品种。缅因猫体格强壮，被毛厚密，长像与西伯利亚森林猫相似，在猫类中亦属大体型的品种。">
            <el-button class="cat_text" slot="reference" type="text">缅因猫</el-button>
          </el-popover>
        </div>
        <div class="cat_info_div">
          <div class="cat_img_div">
            <img class="cat_img" src="../assets/暹罗猫.png">
          </div>
          <el-popover
              placement="bottom"
              width="350"
              trigger="hover"
              content="外表颜色会比较分明，长得很有特点，外表通常是黑白相间，和一般的猫长得都不一样，比较容易辨认，性情比较活泼，容易拆家。">
            <el-button class="cat_text" slot="reference" type="text">暹罗猫</el-button>
          </el-popover>
        </div>
        <div class="cat_info_div">
          <div class="cat_img_div">
            <img class="cat_img" src="../assets/布偶猫.png">
          </div>
          <el-popover
              placement="bottom"
              width="350"
              trigger="hover"
              content="体型较小，长得很像布偶，抱起来就像是在抱着一只布偶一般，多以白色为主，两个耳朵直立起来小小的，很讨人喜爱。">
            <el-button class="cat_text" slot="reference" type="text">布偶猫</el-button>
          </el-popover>
        </div>
        <div class="cat_info_div">
          <div class="cat_img_div">
            <img class="cat_img" src="../assets/蓝猫.png">
          </div>
          <el-popover
              placement="bottom"
              width="350"
              trigger="hover"
              content="体型较壮硕，外表多呈蓝色，大多数出生时都是偏深蓝色，但是随着年龄越来越大，蓝猫的毛色也会越来越浅。">
            <el-button class="cat_text" slot="reference" type="text">蓝猫</el-button>
          </el-popover>
        </div>
        <div class="cat_info_div">
          <div class="cat_img_div">
            <img class="cat_img" src="../assets/波斯猫.png">
          </div>
          <el-popover
              placement="bottom"
              width="350"
              trigger="hover"
              content="是一种经常能够见得到的长毛猫品种，因为出产地是古代的波斯王国而得名，波斯猫是一种外表看上去就比较高贵的猫。">
            <el-button class="cat_text" slot="reference" type="text">波斯猫</el-button>
          </el-popover>
        </div>
      </div>
      <div class="introduce">
        <div class="introduce_title">“养猫人”简介</div>
        <div class="introduce_text">
          养猫人旨在为那些想要寻找新宠物的人提供一个可靠、透明以及积极的选择，同时给予无家可归的猫咪一个温暖的怀抱，双向奔赴。我们坚信，通过推广领养代替购买的理念，可以有效缓解流浪猫问题，并创造更加健康、幸福的家庭环境。我们的初衷是希望通过这个系统将有需要的猫咪和他们的最佳人选联系起来。我们希望为他们提供最好的护理、食物以及爱心。
        </div>
        <img class="introduce_img" src="../assets/introduce.png">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      imgList:[
          require("../assets/img1.png"),
          require("../assets/img2.png"),
          require("../assets/img3.png")
      ]
    }
  }
}
</script>

<style scoped>
  .page{
    display: flex;
    flex-wrap: wrap;
    min-width: 1143px;
    width: 100vm;
    min-height: calc(100vh - 80px);
    background: -webkit-linear-gradient(#162050, #B4B4DC);
    background: -o-linear-gradient(#162050, #B4B4DC);
    background: -moz-linear-gradient(#162050, #B4B4DC);
    background: linear-gradient(#162050, #B4B4DC);
    background-attachment: fixed;
    padding-top: 80px;
  }
  .main{
    display: flex;
    flex-wrap: wrap;
    min-width: 1143px;
    min-height: calc(100vh - 80px);
    width: 60%;
    margin: 0 auto;
    /*background: rgba(10,10,10,0.3);*/
  }
  .show-img{
    width: 100%;
    height: 100%;
  }
  .cat_div{
    margin-top: 140px;
    margin-bottom: 180px;
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    text-align: center;
  }
  .cat_info_div{
    width: 20%;
  }
  .cat_img_div{
    width: 100%;
  }
  .cat_img{
    width: 150px;
    height: 150px;
    border-radius: 100px;
    transition: 0.3s ease-in;
  }
  .cat_img:hover{
    transform: scale(1.1,1.1) translateY(-5%);
    transition: 0.3s ease-in;
    box-shadow: 0px 0px 20px 10px rgb(220,220,220);
  }
  .cat_text{
    color: black;
    font-size: x-large;
    font-weight: bolder;
  }
  .introduce{
    width: 100%;
    height: 500px;
    display: flex;
    flex-wrap: wrap;
  }
  .introduce_title{
    width: 100%;
    height: 80px;
    font-size: xx-large;
    font-weight: bolder;
    text-align: center;
  }
  .introduce_text{
    width: 40%;
    height: 400px;
    line-height: 50px;
    letter-spacing: 1px;
    padding: 5px 10px 10px 10px;
    font-size: larger;
    background: rgba(255,255,255,0.05);
  }
  .introduce_img{
    width: 60%;
    height: 400px;
  }
</style>