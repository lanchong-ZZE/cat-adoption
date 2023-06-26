<template>
  <div>
    <Header/>
    <router-view />
    <Footer/>
  </div>
</template>

<script>
import Header from "@/views/header";
import Footer from "@/views/footer";

export default {
  name: "cat&people",
  components: {Header,Footer}
}
</script>

<style>
</style>