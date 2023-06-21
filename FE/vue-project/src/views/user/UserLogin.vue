
<template>
    <div>
      <h2>Login</h2>
      <input type="text" v-model="id" placeholder="Id" />
      <input type="password" v-model="password" placeholder="Password" />
      <button @click="loginCheck">Login</button>
      <p v-if="loginError" class="error">{{ loginError }}</p>

      <p></p>
      <router-link to="/register">혹시 아이디가 없으신가요? 회원가입하기</router-link>
      <p></p>
      <router-link to="/">돌아가기</router-link>

    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  export default {
    data() {
      return {
        id: '',
        password: '',
        loginError: '',
      };
    },
    methods: {
      ...mapActions("userStore", ["login"]),
      loginCheck() {
        this.login({id : this.id, password : this.password});

        // if (this.username === 'admin' && this.password === 'password') {
        //   this.$store.commit('login'); // Vuex store의 'login' mutation을 호출하여 로그인 상태를 변경합니다.
        //   this.loginError = '';
        // } else {
        //   this.loginError = 'Invalid username or password.';
        // }
      },
    },
  };
  </script>
  
  <style scoped>
  .error {
    color: red;
  }
  </style>
  