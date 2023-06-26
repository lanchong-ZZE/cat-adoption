<template>
  <div class="page0">
    <div class="main0">
      <Left/>
      <router-view />
    </div>
  </div>
</template>

<script>
import Left from "@/views/personal_leftside";
export default {
  name: "personal",
  components: {Left}
}
</script>

<style scoped>
  .page0{
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
  .main0{
    min-width: 1143px;
    width: 60%;
    margin: auto;
    display: flex;
    flex-wrap: wrap;
    overflow: hidden;
  }
</style>