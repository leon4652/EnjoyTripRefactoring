<template>
    <div>
      <h2>최초 인증 안내</h2>
      
      <div>사용자님의 메일 주소 ({{ this.userInfo.userEmail}})로 입력된 인증 번호를 입력해 주세요!</div>
      <div></div>

      <p></p>
      <div>
        <input type="text" v-model="code" placeholder="인증 번호(8자리)" />
        <button @click="checkFirstRegist">입력</button>
      </div>

      <p></p>
      <router-link to="/">
      <button>돌아가기</button>
      </router-link>
    </div>
  </template>
  
  <script>
  import { mapActions, mapState } from 'vuex';
 
  export default {
    name: 'FirstAuth',
    components: {  },
    data() {
      return {
        code : '',
      };
    },
    methods : { 
      ...mapActions('userStore', ['sendMail', 'checkFirstAuthCode']),
      async checkFirstRegist() {
        const result = await this.checkFirstAuthCode({ code : this.code});
        if(result) {
          alert("인증이 완료되었습니다. 가입을 환영합니다!")
          this.$router.push("/");
        }
        else {
          alert("틀린 인증번호입니다.")
        }
      }
     },
     computed : {
        ...mapState('userStore', ['userInfo'])
     }, 
  };
  </script>
  <style scoped>
  </style>
  