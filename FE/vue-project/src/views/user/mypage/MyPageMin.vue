<template>
    <div class="outer-container">
        <div v-if="userInfo !== null" class="after-login">
            안녕하세요, {{ this.userInfo.userName }} 님!
            <div class="button-container-gap-min">
                <router-link to="/mypage">
                    <button>마이메이지</button>
                </router-link>
                <button @click="logout">로그아웃</button>
            </div>
        </div>
        <div v-else>
            로그인 해주세요.
            <p></p>
            <div class="button-container-gap-min">
                <router-link to="/login">
                    <button>로그인</button>
                </router-link>
                <router-link to="/register">
                    <button>회원가입</button>
                </router-link>
            </div>
        </div>
    </div>
</template>
  
<script>

import { mapState, mapMutations } from 'vuex';
import "@/assets/style/btn/mainBtn.css"
export default {
    data() {
        return {
        };
    },
    computed: {
        ...mapState("userStore", ['userInfo']),
    },
    components: {

    },
    methods: {
        ...mapMutations("userStore", ['setLoggedIn']),
        logout() {
            this.setLoggedIn(false);
        }
    },
};
</script>
  
<style >

.outer-container {
    display: flex;
    justify-content: center;
    /* 요소들을 수평으로 중앙에 정렬 */
    align-items: center;
    /* 요소들을 수직으로 중앙에 정렬 */

    border: 1px solid;
    color: #000;
    width: 300px;
    min-height: 80px;

    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    /* 그림자 효과 추가 */
    background-color: #ffffff;
    border: 1px solid #ccc;
}
</style>
  