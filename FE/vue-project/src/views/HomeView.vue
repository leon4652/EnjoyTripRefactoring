<template>
    <div>
      <nav-bar></nav-bar>
      안녕하세요. homeview입니다.
      메인 페이지입니다.
      
      <button @click="mailEvent">메일보내기</button>
      
    </div>
  </template>
  
  <script>
  import NavBar from './NavBar.vue';
  import { mapActions } from 'vuex';

  export default {
    name: 'HomeView',
    components: { NavBar },
    methods : { 
      ...mapActions('userStore', ['sendMail']),
      mailEvent() {
        this.sendMail();
      }
     },
  };
  </script>
  <style scoped>
  </style>
  