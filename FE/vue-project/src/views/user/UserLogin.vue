

<template>
  <div>
    <div class="outer-container">
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
  </div>
  </template>
  
  <script>
  import { mapActions, mapState } from 'vuex';
  export default {
    data() {
      return {
        id: '',
        password: '',
        loginError: '',
      };
    },
    computed: {
      ...mapState("userStore", ["loggedIn"]),
    },
    methods: {
      ...mapActions("userStore", ["login"]),
      async loginCheck() {
    try {
      await this.login({id : this.id, password : this.password})
    } catch (error) {
      console.error(error);
    }
      if(this.loggedIn) {
        this.$router.push('/');
      }
      else {
        this.loginError = 'Invalid username or password.';
      }

      },
    },
  };
  </script>
  
  <style scoped>
  .outer-container {
    
    display: flex;
    flex-direction: column;

    max-height: 350px;
    max-width: 550px;

    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0 auto;

    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* 그림자 효과 추가 */
    background-color: #ffffff;
    border: 1px solid #ccc; 
  }
  .error {
    color: red;
  }
  </style>
  