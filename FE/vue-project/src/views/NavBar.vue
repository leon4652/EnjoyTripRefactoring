<template>
  <div>
    <div class = "">
      <router-link to="/">HOME</router-link> |
      <router-link to="/test">TEST</router-link> |
      <router-link to="/login">로그인</router-link> |
      <router-link to="/freeboard">자유게시판</router-link>
    </div>
    <div class = "my-page-min">
      <my-page-min></my-page-min>
    </div>
  </div>
</template>
  
<script>

import MyPageMin from './user/mypage/MyPageMin.vue';
export default {
  name: 'NavBar',
  components: { MyPageMin },
};
</script>
<style scoped>

.my-page-min {
  margin-top: 50px;
  margin-right: 50px;
  display: flex;
  justify-content: flex-end; /* 요소들을 우측으로 정렬 */
}
</style>
  