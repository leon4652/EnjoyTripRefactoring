<template>
  <div>
    <div class="outer-container">
      <h2>회원 등록</h2>
      <div>
        <label for="name">아이디 : </label>
        <input type="text" id="id" v-model="id" @blur="checkId" required />
      </div>
      <div>
        <label for="password">비밀번호 : </label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div>
        <label for="email">이메일 : </label>
        <input type="email" id="email" v-model="email" @blur="checkEmail" required />
      </div>

      <div>
        <label for="name">이름 : </label>
        <input type="text" id="name" v-model="name" required />
      </div>


      <div class="button-container">
        <button type="submit" @click="registerUser">등록</button>
        <button type="submit" @click="goHome">취소</button>
      </div>
      <p></p>
      <div class="error">{{ checkInfo }}</div>

    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
  data() {
    return {
      name: '',
      email: '',
      password: '',
      id: '',
      checkInfo: '',
    };
  },
  
  methods: {
    ...mapActions("userStore", ["register", "checkDuplicate"]),
    async registerUser() {
      //1. ID중복 / 길이 검사
      let isDuplicate = await this.checkDuplicate({ input: this.id, type: 'user_id' });
      if(isDuplicate || this.id.length < 7) {
        if(isDuplicate) alert("중복된 아이디입니다.")
        else alert("아이디는 7자 이상으로 작성해주세요.")
        return;
      }
      //2. 이름 중복 / 길이 검사
      isDuplicate = await this.checkDuplicate({ input: this.name, type: 'user_name' });
      if(isDuplicate || this.name.length < 2) {
        if(this.name.length < 2) alert("이름이 너무 짧습니다.")
        else alert("중복된 이름입니다.") 
        return;
      }
      //3. 이메일 형식 체크
      if(this.email.length < 7 || !this.email.includes('@')) {
        alert("잘못된 이메일 형식입니다.")
      }

      //등록 로직
      try {
            await this.register({ id: this.id, password: this.password, email: this.email, name: this.name })
            alert("회원 가입을 완료하였습니다!")
            this.$router.push("/");
          }
       catch (error) {
        console.error(error);
      }

    },
    goHome() {
      this.$router.push('/');
    },

    async checkId() {
      if(this.id.length < 7) {
        this.checkInfo = "ID는 7자 이상으로 설정해주세요."
      }
      else {
      try {
        if(await this.checkDuplicate({input : this.id, type : "user_id"})) {
          this.checkInfo = "중복된 ID입니다."
        }
        else {
          this.checkInfo = ""
        }
      } catch (error) {
        console.error(error);
      }
    }
    },

    async checkEmail() {
      if(this.email.length < 7 || !this.email.includes('@')) {
        this.checkInfo = "잘못된 이메일 형식입니다."
      }
      else {
        this.checkInfo = ""
      }
    }
  }
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
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  /* 그림자 효과 추가 */
  background-color: #ffffff;
  border: 1px solid #ccc;
}

h2 {
  margin-bottom: 20px;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
}

input {
  margin-bottom: 10px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-container {
  display: flex;
  justify-content: space-around;
  gap: 100px;
  /* 버튼 사이의 간격 설정 */
}


button {
  padding: 10px 15px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #003f80;
}

button:focus {
  outline: none;
}

.error {
    color: red;
  }
</style>
