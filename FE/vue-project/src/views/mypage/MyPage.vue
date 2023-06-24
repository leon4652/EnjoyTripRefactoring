<template>
  <div>
    여기 마이페이지 들어갈 예정입니다.

  <button @click="goHome">돌아가기</button>


  </div>
</template>
  
<script>

export default {
  components: {},
  methods: {
    goHome() {
      this.$router.push("/");
    }
  },
};
</script>
<style scoped></style>
  